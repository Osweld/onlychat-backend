package german.dev.onlychatbackend.rol.service;

import german.dev.onlychatbackend.rol.entity.Rol;
import german.dev.onlychatbackend.rol.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
}
