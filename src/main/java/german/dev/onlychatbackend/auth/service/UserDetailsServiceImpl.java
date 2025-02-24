package german.dev.onlychatbackend.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import german.dev.onlychatbackend.auth.models.AuthUser;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRol().getRole()));

        boolean isActive = user.getUserStatus().getId() == (UserStatusEnum.ACTIVE.getId());
        boolean isBlocked = user.getUserStatus().getId() != (UserStatusEnum.BLOCKED.getId());
        boolean isDeleted = user.getUserStatus().getId() != (UserStatusEnum.DELETED.getId());

        return new AuthUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            isActive,              
            isDeleted,                  
            true,                  
            isBlocked,          
            authorities
        );
    }
}
