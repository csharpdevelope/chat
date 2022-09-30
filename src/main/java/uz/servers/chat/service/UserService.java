package uz.servers.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.servers.chat.exception.BadRequestException;
import uz.servers.chat.model.User;
import uz.servers.chat.payload.request.UserRequest;
import uz.servers.chat.payload.response.UserResponse;
import uz.servers.chat.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse addUser(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if (user != null) {
            throw new BadRequestException("This user exist");
        }
        user = new User();
        user.setUsername(userRequest.getUsername());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getUsername());
    }
}
