package uz.servers.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.servers.chat.exception.BadRequestException;
import uz.servers.chat.exception.NotFoundException;
import uz.servers.chat.model.*;
import uz.servers.chat.payload.request.MessageGetRequest;
import uz.servers.chat.payload.request.MessageRequest;
import uz.servers.chat.payload.response.ChatGetResponse;
import uz.servers.chat.payload.response.MessageGetResponse;
import uz.servers.chat.payload.response.MessageResponse;
import uz.servers.chat.repo.ChatRepository;
import uz.servers.chat.repo.MessageRepository;
import uz.servers.chat.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public MessageResponse addTextMessage(MessageRequest request) {
        Chat chat = (Chat) isPre(new Chat(), request);
        User user = (User) isPre(new User(), request);
        boolean isConnection = false;
        for (User existUser: chat.getUsers()) {
            if (existUser.equals(user)) {
                isConnection = true;
                break;
            }
        }
        if (!isConnection) {
            throw new BadRequestException("User id va Chat id biriga mos emas");
        }
        Message message = new Message();
        message.setContent(request.getContent());
        message.setChat(chat);
        message.setType(MessageType.TEXT);
        message.setUser(user);
        messageRepository.save(message);
        return new MessageResponse(message.getId());
    }

    public MessageResponse addImageMessage(MessageRequest request) {
        Chat chat = (Chat) isPre(new Chat(), request);
        User user = (User) isPre(new User(), request);
        boolean isConnection = false;
        for (User existUser: chat.getUsers()) {
            if (existUser.equals(user)) {
                isConnection = true;
                break;
            }
        }
        if (!isConnection) {
            throw new BadRequestException("User id va Chat id biriga mos emas");
        }
        Attachment images =  fileUploadService.uploadFilename(request.getContent(), request.getExt());
        if (images == null) {
            return null;
        }
        Message message = new Message();
        message.setChat(chat);
        message.setType(MessageType.IMAGE);
        message.setUser(user);
        message.setImage(images);
        messageRepository.save(message);
        return new MessageResponse(message.getId());
    }

    private void isPresent(MessageRequest request) {
        Optional<Chat> chatOptional = chatRepository.findById(request.getChatId());
        if (chatOptional.isEmpty()) {
            throw new NotFoundException("Bunday Chat mavjud emas");
        }
        Optional<User> userOptional = userRepository.findById(request.getAuthorId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Bunday User mavjud emas");
        }
    }

    private Object isPre(Object object, MessageRequest request) {
        if (object instanceof User) {
            Optional<User> userOptional = userRepository.findById(request.getAuthorId());
            if (userOptional.isEmpty()) {
                throw new NotFoundException("Bunday User mavjud emas");
            }
            return userOptional.get();
        }

        if (object instanceof Chat) {
            Optional<Chat> chatOptional = chatRepository.findById(request.getChatId());
            if (chatOptional.isEmpty()) {
                throw new NotFoundException("Bunday Chat mavjud emas");
            }
            return chatOptional.get();
        }
        return null;
    }

    public List<MessageGetResponse> getAllMessage(MessageGetRequest request) {
        Optional<Chat> chat = chatRepository.findById(request.getChatId());
        if (chat.isEmpty()) {
            throw new NotFoundException("Bunday id boyicha Chat mavjud emas");
        }
        List<Message> messages = messageRepository.findAllByChatOrderByCreateAt(chat.get());
        List<MessageGetResponse> responses = new ArrayList<>();
        for(Message message: messages) {
            MessageGetResponse response = new MessageGetResponse();
            response.setId(message.getId());
            response.setType(message.getType());
            response.setCreateAt(message.getCreateAt());
            if (message.getType() == MessageType.TEXT){
                response.setContent(message.getContent());
            } else {
                response.setUrl(message.getImage().getFilename());
            }
            responses.add(response);
        }
        return responses;
    }
}
