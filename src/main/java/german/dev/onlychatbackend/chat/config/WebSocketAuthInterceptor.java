package german.dev.onlychatbackend.chat.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import german.dev.onlychatbackend.auth.model.AuthUser;
import german.dev.onlychatbackend.auth.service.JwtService;
import german.dev.onlychatbackend.auth.service.JwtServiceImpl;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // Para logging
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public WebSocketAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.debug("Processing WebSocket CONNECT command");

            String authHeader = accessor.getFirstNativeHeader(JwtServiceImpl.HEADER_STRING);
            
            // Caso 1: No hay token o no tiene el formato correcto
            if (authHeader == null || !authHeader.startsWith(JwtServiceImpl.TOKEN_PREFIX)) {
                log.warn("No valid Bearer token found in WebSocket connection request");
                throw new MessagingException("Unauthorized: No valid authentication token provided");
            }
            
            String token = authHeader.substring(7);
            
            try {
                // Caso 2: Token inválido
                if (!jwtService.validate(token)) {
                    log.warn("Invalid JWT token in WebSocket connection");
                    throw new MessagingException("Unauthorized: Invalid authentication token");
                }
                
                // Token válido - proceder con la autenticación
                Long id = jwtService.getId(token);
                String username = jwtService.getUsername(token);
                Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorities(token);
                
                AuthUser principal = new AuthUser(
                        id,
                        username,
                        null,
                        true, true, true, true,
                        authorities);
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(principal, null, authorities);
                
                accessor.setUser(authentication);
                log.info("WebSocket connection authenticated for user: {}", username);
                
            } catch (JwtException | IOException e) {
                // Caso 3: Error al procesar el token
                log.error("JWT token processing error", e);
                throw new MessagingException("Unauthorized: Error processing authentication token");
            }
        }
        
        return message;
    }
}
