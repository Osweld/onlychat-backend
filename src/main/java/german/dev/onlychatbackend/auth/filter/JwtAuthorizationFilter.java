package german.dev.onlychatbackend.auth.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import german.dev.onlychatbackend.auth.models.AuthUser;
import german.dev.onlychatbackend.auth.service.JwtService;
import german.dev.onlychatbackend.auth.service.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String header = request.getHeader(JwtServiceImpl.HEADER_STRING);
        
        if(Boolean.TRUE.equals(jwtService.requiresAuthentication(header))) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(header);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) throws IOException {
        if (jwtService.validate(token)) {
            Long id = jwtService.getId(token);
            String username = jwtService.getUsername(token);
            Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorities(token);

            AuthUser principal = new AuthUser(
                id,
                username,
                "",
                true,
                true,
                true,
                true, 
                authorities
            );

            return new UsernamePasswordAuthenticationToken(
                principal,
                null,
                authorities
            );
        }
        return null;
    }
}
