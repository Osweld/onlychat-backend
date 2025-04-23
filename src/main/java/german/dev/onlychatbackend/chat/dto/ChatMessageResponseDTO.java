package german.dev.onlychatbackend.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDTO {
    private Long id;                
    private Long chatId;           
    private String message;         
    private LocalDateTime timestamp; 
    private Long senderId;          
    private String senderUsername;  
    
    
}
