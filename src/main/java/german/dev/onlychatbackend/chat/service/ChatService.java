package german.dev.onlychatbackend.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import german.dev.onlychatbackend.chat.dto.CreatePersonalChatRequestDTO;
import german.dev.onlychatbackend.chat.dto.CreatePersonalChatResponseDTO;
import german.dev.onlychatbackend.chat.dto.UserChatsResponseDTO;
import german.dev.onlychatbackend.chat.projection.UserSearchProjection;

public interface ChatService {

    UserChatsResponseDTO getUserChats(Long userId);
    CreatePersonalChatResponseDTO createPersonalChat(String username, CreatePersonalChatRequestDTO createPersonalChatRequestDTO);
    Page<UserSearchProjection> searchUsers(String username, Pageable pageable);
}
