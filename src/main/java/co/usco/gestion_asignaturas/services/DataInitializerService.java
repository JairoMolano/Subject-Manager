package co.usco.gestion_asignaturas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.usco.gestion_asignaturas.models.AsignaturaModel;
import co.usco.gestion_asignaturas.models.PermissionModel;
import co.usco.gestion_asignaturas.models.RoleModel;
import co.usco.gestion_asignaturas.models.UserModel;
import co.usco.gestion_asignaturas.repositories.RoleRepository;
import co.usco.gestion_asignaturas.repositories.UserRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class DataInitializerService {

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void initializeData() {

        // ROLES
        RoleModel roleRector = RoleModel.builder()
            .rolename("RECTOR")
            .permissions(Set.of(PermissionModel.READ, PermissionModel.MODIFY, PermissionModel.CREATE, PermissionModel.DELETE))
            .build();

        RoleModel roleDocente = RoleModel.builder()
            .rolename("DOCENTE")
            .permissions(Set.of(PermissionModel.READ, PermissionModel.MODIFY))
            .build();

        RoleModel roleEstudiante = RoleModel.builder()
        .rolename("ESTUDIANTE")
        .permissions(Set.of(PermissionModel.READ))
        .build();

        roleRepository.saveAll(List.of(roleDocente, roleEstudiante, roleRector));

        // USUARIOS
        UserModel userDocente1 = UserModel.builder()
            .username("DOCENTE1")
            .password(passwordEncoder.encode("1234"))
            .roles(Set.of(roleDocente))
            .build();

        UserModel userDocente2 = UserModel.builder()
            .username("DOCENTE2")
            .password(passwordEncoder.encode("1234"))
            .roles(Set.of(roleDocente))
            .build();

        UserModel userRector = UserModel.builder()
            .username("RECTOR")
            .password(passwordEncoder.encode("1234"))
            .roles(Set.of(roleRector))
            .build();

        UserModel userEstudiante = UserModel.builder()
            .username("ESTUDIANTE1")
            .password(passwordEncoder.encode("1234"))
            .roles(Set.of(roleEstudiante))
            .build();

        userRepository.saveAll(List.of(userDocente1, userDocente2, userRector, userEstudiante));

        // CREACION DE ASIGNATURAS
        AsignaturaModel asignaturaMatematicas = AsignaturaModel.builder()
            .name("MATEMATICAS")
            .descripcion("ASIGNATURA DE MATEMATICAS")
            .salon(104)
            .horario(LocalTime.parse("08:00"))
            .build();
        
        AsignaturaModel asignaturaFisica = AsignaturaModel.builder()
            .name("FISICA")
            .descripcion("ASIGNATURA DE FISICA")
            .salon(105)
            .horario(LocalTime.parse("09:00"))
            .build();

        AsignaturaModel asignaturaLenguaje = AsignaturaModel.builder()
            .name("LENGUAJE")
            .descripcion("ASIGNATURA DE LENGUAJE")
            .salon(204)
            .horario(LocalTime.parse("10:00"))
            .build();

        AsignaturaModel asignaturaHistoria = AsignaturaModel.builder()
            .name("HISTORIA")
            .descripcion("ASIGNATURA DE HISTORIA")
            .salon(205)
            .horario(LocalTime.parse("11:00"))
            .build();

        // ASIGNACION DE ASIGNATURAS
        userDocente1.setAsignaturas(Set.of(asignaturaMatematicas, asignaturaFisica));
        userDocente2.setAsignaturas(Set.of(asignaturaLenguaje, asignaturaHistoria));
        userEstudiante.setAsignaturas(Set.of(asignaturaMatematicas, asignaturaLenguaje, asignaturaHistoria));

    }
}
