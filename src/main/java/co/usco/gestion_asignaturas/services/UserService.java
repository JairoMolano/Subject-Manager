package co.usco.gestion_asignaturas.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import co.usco.gestion_asignaturas.models.UserModel;
import co.usco.gestion_asignaturas.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<UserModel> getAllUsersWithDocenteRole() {
        return userRepository.findAll().stream()
            .filter(user -> user.getRoles().stream()
                .anyMatch(role -> role.getRolename().equals("DOCENTE")))
            .collect(Collectors.toList());
    }

    public UserModel getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
