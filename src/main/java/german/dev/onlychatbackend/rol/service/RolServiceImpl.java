package german.dev.onlychatbackend.rol.service;

import german.dev.onlychatbackend.rol.entity.Rol;
import german.dev.onlychatbackend.rol.exception.RolNotFoundException;
import german.dev.onlychatbackend.rol.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        try{
            return rolRepository.findAll();
        }catch (Exception e){
            throw new RolNotFoundException("Error while fetching roles");
        }
        
    }
}
