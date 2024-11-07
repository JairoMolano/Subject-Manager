package co.usco.gestion_asignaturas.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import co.usco.gestion_asignaturas.repositories.AsignaturaRepository;
import co.usco.gestion_asignaturas.repositories.UserRepository;
import co.usco.gestion_asignaturas.models.AsignaturaModel;
import co.usco.gestion_asignaturas.models.UserModel;
import java.util.Optional;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    // GET ALL ASIGNATURAS SERVICE
    public List<AsignaturaModel> getAllAsignaturas() {
        return asignaturaRepository.findAll();
    }

    @Autowired
    private UserRepository userRepository;

    // GET ASIGNATURAS BY USER SERVICE
    public List<AsignaturaModel> getAsignaturasByUser(String username) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<AsignaturaModel> asignaturas = new ArrayList<>(user.getAsignaturas());
        return asignaturas;
    }


    // GET ASIGNATURA BY NAME SERVICE
    public AsignaturaModel getAsignaturaByName(String name) {
        return asignaturaRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // GET ASIGNATURA BY ID SERVICE
    public Optional<AsignaturaModel> getAsignaturaById(Long id) {
        return asignaturaRepository.findById(id);
    }


    // CREATE ASIGNATURA SERVICE
    public AsignaturaModel createAsignatura(String name, String description, String credits, String hours, String docente) {
        AsignaturaModel asignatura = AsignaturaModel.builder()
            .name(name)
            .descripcion(description)
            .salon(Integer.parseInt(credits))
            .horario(LocalTime.parse(hours))
            .build();
        UserModel user = userRepository.findByUsername(docente).orElseThrow(() -> new RuntimeException("User not found"));    
        user.getAsignaturas().add(asignatura);
        return asignaturaRepository.save(asignatura);
    }

    // UPDATE ASIGNATURA SERVICE
    public AsignaturaModel updateAsignatura(Long id, String hours) {
        AsignaturaModel asignatura = asignaturaRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        asignatura.setHorario(LocalTime.parse(hours));
        return asignaturaRepository.save(asignatura);
    }

    // DELETE ASIGNATURA SERVICE
    public void deleteUser(Long id) { 
        asignaturaRepository.deleteById(id);  
    }

}
