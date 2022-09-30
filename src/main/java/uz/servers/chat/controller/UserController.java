package uz.servers.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.servers.chat.exception.BadRequestException;
import uz.servers.chat.payload.request.UserRequest;
import uz.servers.chat.payload.response.UserResponse;
import uz.servers.chat.service.UserService;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        if (userRequest.getUsername().isEmpty() && userRequest.getUsername() == null) {
            throw new BadRequestException("Username null bolmasligi kerak");
        }
        UserResponse response = userService.addUser(userRequest);
        return ResponseEntity.ok(response);
    }
}
