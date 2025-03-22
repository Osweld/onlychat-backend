package german.dev.onlychatbackend.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.exception.MessageNotFoundException;
import german.dev.onlychatbackend.chat.mapper.MessageMapper;
import german.dev.onlychatbackend.chat.projection.ChatMessageProjection;
import german.dev.onlychatbackend.chat.repository.ChatUserRepository;
import german.dev.onlychatbackend.chat.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatUserRepository chatUserRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ChatUserRepository chatUserRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.chatUserRepository = chatUserRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageProjection> getMessagesByChatId(Long chatId, Long userId) {
        if (chatUserRepository.existsByChatIdAndUserId(chatId, userId)) {
            return messageRepository.getMessagesByChatId(chatId);
        } else {
            throw new MessageNotFoundException("User is not part of the chat");
        }
    }

    @Override
    @Transactional
    public void sendMessage(SendMessageDTO message,Long userId) {
        if (chatUserRepository.existsByChatIdAndUserId(message.getChatId(), userId)) {
            messageRepository.save(messageMapper.toEntity(message, userId));
        } else {
            throw new MessageNotFoundException("User is not part of the chat");
        }
    
    }
}
