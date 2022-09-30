package uz.servers.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.servers.chat.exception.BadRequestException;
import uz.servers.chat.exception.NotFoundException;
import uz.servers.chat.model.Chat;
import uz.servers.chat.model.User;
import uz.servers.chat.payload.request.ChatGetRequest;
import uz.servers.chat.payload.request.ChatRequest;
import uz.servers.chat.payload.response.ChatGetResponse;
import uz.servers.chat.payload.response.ChatResponse;
import uz.servers.chat.repo.ChatRepository;
import uz.servers.chat.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatResponse saveChat(ChatRequest request) {
        Chat chat = new Chat();
        List<User> users = new ArrayList<>();
        for(Long username: request.getUsers()) {
            Optional<User> user = userRepository.findById(username);
            users.add(user.get());
        }
        if (users.isEmpty()) {
            throw new BadRequestException("Bunday userlar mavjud emas");
        }
        chat.setName(request.getName());
        chat.setUsers(users);
        chatRepository.save(chat);
        return new ChatResponse(chat.getId(), chat.getName());
    }

    public List<ChatGetResponse> getAllEndChat(ChatGetRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()){
            throw new NotFoundException("Ushbu id boyicha hech qanday user mavjud emas");
        }
        List<Chat> chats = chatRepository.findAllByUsersOrderByCreateAtDesc(user.get());
        List<ChatGetResponse> responses = new ArrayList<>();
        for (Chat chat : chats) {
            responses.add(new ChatGetResponse(chat.getId(), chat.getName(), chat.getCreateAt()));
        }
        return responses;
    }
}
