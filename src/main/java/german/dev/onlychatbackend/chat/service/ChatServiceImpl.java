package german.dev.onlychatbackend.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import german.dev.onlychatbackend.chat.dto.CreatePersonalChatRequestDTO;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;
import german.dev.onlychatbackend.chat.dto.UserChatsResponseDTO;
import german.dev.onlychatbackend.chat.entity.Chat;
import german.dev.onlychatbackend.chat.entity.ChatType;
import german.dev.onlychatbackend.chat.entity.ChatUser;
import german.dev.onlychatbackend.chat.enums.ChatTypeEnum;
import german.dev.onlychatbackend.chat.mapper.ChatMapper;
import german.dev.onlychatbackend.chat.projection.UserSearchProjection;
import german.dev.onlychatbackend.chat.repository.ChatRepository;
import german.dev.onlychatbackend.chat.repository.ChatUserRepository;
import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.enums.UserStatusEnum;
import german.dev.onlychatbackend.user.exception.UserNotFoundException;
import german.dev.onlychatbackend.user.repository.UserRepository;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {

    private static final Long ACTIVE_STATUS_ID = Long.valueOf(UserStatusEnum.ACTIVE.getId());
    private static final Long PERSONAL_CHAT_ID = Long.valueOf(ChatTypeEnum.PERSONAL.getId());

    private final UserRepository userRepository;
    private final ChatUserRepository chatUserRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public ChatServiceImpl(UserRepository userRepository, ChatUserRepository chatUserRepository,
            ChatRepository chatRepository, ChatMapper chatMapper) {
        this.userRepository = userRepository;
        this.chatUserRepository = chatUserRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserChatsResponseDTO getUserChats(Long userId) {
        User user = getActiveUserById(userId);

        UserChatsResponseDTO userChatsResponseDTO = new UserChatsResponseDTO();
        userChatsResponseDTO.setId(user.getId());
        userChatsResponseDTO.setUsername(user.getUsername());
        userChatsResponseDTO.setChats(chatUserRepository.findByUserId(userId));
        return userChatsResponseDTO;
    }

    @Override
    @Transactional
    public CreatePersonalChatResponseDTO createPersonalChat(String username, CreatePersonalChatRequestDTO requestDTO) {
        User user = getActiveUserByUsername(username);
        User otherUser = getActiveUserByUsername(requestDTO.getUsername());

        existingPersonalChat(generatePersonalChatname(user, otherUser), PERSONAL_CHAT_ID);

        Chat chat = new Chat();
        chat.setChatName(generatePersonalChatname(user, otherUser));
        chat.setChatType(new ChatType(PERSONAL_CHAT_ID));
        chatRepository.save(chat);
        createChatUser(user, chat);
        createChatUser(otherUser, chat);

        return chatMapper.toCreatePersonalChatResponseDTO(chat.getId(), chat.getChatName(),
                chat.getChatType().getName(), user.getUsername(), otherUser.getUsername());

    }

    private void createChatUser(User user, Chat chat) {
        ChatUser chatUser = new ChatUser();
        chatUser.setUser(user);
        chatUser.setChat(chat);
        chatUserRepository.save(chatUser);
    }

    private String generatePersonalChatname(User user1, User user2) {
        Long smallerId = Math.min(user1.getId(), (user2.getId()));
        Long biggerId = Math.max(user1.getId(), (user2.getId()));
        return "personal_" + smallerId + " - " + biggerId;
    }

    private void validateUserIsActive(User user) {
        if (!Objects.equals(user.getUserStatus().getId(), ACTIVE_STATUS_ID)) {
            throw new UserNotFoundException("User is not active");
        }
    }

    private User getActiveUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        validateUserIsActive(user);
        return user;
    }

    private User getActiveUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        validateUserIsActive(user);
        return user;
    }

    private void existingPersonalChat(String chatName, Long chatTypeId) {
        if (chatRepository.existsByChatNameAndChatTypeId(chatName, chatTypeId)) {
            throw new IllegalArgumentException("Personal chat already exists");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSearchProjection> searchUsers(String username, Pageable pageable) {
        return userRepository.searchActiveUserByUsername(username, pageable);
    }

}
