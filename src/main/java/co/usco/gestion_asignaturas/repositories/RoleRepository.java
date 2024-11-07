package co.usco.gestion_asignaturas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.usco.gestion_asignaturas.models.RoleModel;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long>{
 
    Optional<RoleModel> findByRolename(String rolename);
}
