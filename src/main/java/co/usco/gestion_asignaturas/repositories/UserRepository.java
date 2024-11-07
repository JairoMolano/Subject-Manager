package co.usco.gestion_asignaturas.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.usco.gestion_asignaturas.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    Optional<UserModel> findByUsername(String username);
    
}
