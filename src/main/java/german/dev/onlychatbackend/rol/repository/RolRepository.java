package german.dev.onlychatbackend.rol.repository;

import german.dev.onlychatbackend.rol.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRol(String rol);
}
