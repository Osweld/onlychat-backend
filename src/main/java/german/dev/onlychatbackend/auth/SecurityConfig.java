package german.dev.onlychatbackend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import german.dev.onlychatbackend.auth.filter.JwtAuthorizationFilter;
import german.dev.onlychatbackend.auth.service.JwtService;
import german.dev.onlychatbackend.common.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public SecurityConfig(BCryptPasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        httpSecurity
                .authenticationProvider(provider)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/api/v1/auth/login",
                        "/api/v1/auth/signup",
                        "/swagger-ui/**",
                        "/bus/v3/api-docs/**",
                        "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            ErrorResponse errorResponse = ErrorResponse.builder()
                                    .status(HttpStatus.UNAUTHORIZED.value())
                                    .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                    .message("Token is missing or invalid")
                                    .path(request.getRequestURI())
                                    .build();

                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of(
                                    "error", "Forbidden",
                                    "message", accessDeniedException.getMessage(),
                                    "path", request.getServletPath())));
                        }))
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilter(new JwtAuthorizationFilter(authenticationManager, jwtService));

        return httpSecurity.build();
    }
}
