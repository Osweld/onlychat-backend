package german.dev.onlychatbackend.chat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.chat.dto.MessageSeenNotificationDTO;
import german.dev.onlychatbackend.chat.dto.SendMessageDTO;
import german.dev.onlychatbackend.chat.entity.Message;
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
    private final SimpMessagingTemplate messagingTemplate;

    public MessageServiceImpl(MessageRepository messageRepository, ChatUserRepository chatUserRepository, MessageMapper messageMapper, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.chatUserRepository = chatUserRepository;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatMessageProjection> getMessagesByChatId(Long chatId, Long userId, Pageable pageable) {
        if (chatUserRepository.existsByChatIdAndUserId(chatId, userId)) {
            return messageRepository.getMessagesByChatId(chatId, pageable);
        } else {
            throw new MessageNotFoundException("User is not part of the chat");
        }
    }

    @Override
    @Transactional
    public Message sendMessage(SendMessageDTO message, Long userId) {
        if (chatUserRepository.existsByChatIdAndUserId(message.getChatId(), userId)) {
            return messageRepository.save(messageMapper.toEntity(message, userId));

        } else {
            throw new MessageNotFoundException("User is not part of the chat");
        }
    
    }

    @Override
    @Transactional
    public void markChatAsRead(Long chatId, Long userId) {
        if (chatUserRepository.existsByChatIdAndUserId(chatId, userId)) {
            messageRepository.markMessagesAsRead(chatId, userId);
            notificationToUser(userId, chatId);
        } else {
            throw new MessageNotFoundException("User is not part of the chat");
        }
    }


    private void notificationToUser(Long userId, Long chatId) {

        List<Long> userIds = chatUserRepository.findOtherUserIdsInChat(chatId, userId);
        for (Long id : userIds) {
             messagingTemplate.convertAndSendToUser(
                    id.toString(),
                    "/queue/message-seen",
                    new MessageSeenNotificationDTO(chatId, userId)
                );
        }
        
    }
}
