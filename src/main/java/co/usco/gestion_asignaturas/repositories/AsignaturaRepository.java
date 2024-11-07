package co.usco.gestion_asignaturas.repositories;

import java.util.Optional;
import co.usco.gestion_asignaturas.models.AsignaturaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<AsignaturaModel, Long> {
    
    Optional<AsignaturaModel> findByName(String name);

}
