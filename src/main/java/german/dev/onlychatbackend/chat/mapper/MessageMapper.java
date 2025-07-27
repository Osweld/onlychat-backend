package german.dev.onlychatbackend.chat.mapper;

import org.springframework.stereotype.Service;

import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.entity.Chat;
import german.dev.onlychatbackend.chat.entity.Message;
import german.dev.onlychatbackend.user.entity.User;

@Service
public class MessageMapper {

    public Message toEntity(SendMessageDTO sendMessageDTO, Long senderId) {
        Message message = new Message();
        message.setText(sendMessageDTO.getMessage());
        message.setChat(new Chat(sendMessageDTO.getChatId()));
        message.setUser(new User(senderId));

        return message;
    }

}
