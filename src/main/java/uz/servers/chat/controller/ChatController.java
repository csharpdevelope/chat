package uz.servers.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.servers.chat.payload.request.ChatGetRequest;
import uz.servers.chat.payload.request.ChatRequest;
import uz.servers.chat.payload.response.ChatGetResponse;
import uz.servers.chat.payload.response.ChatResponse;
import uz.servers.chat.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("add")
    public ResponseEntity<?> addChatBetweenUsers(@RequestBody ChatRequest request) {
        ChatResponse response = chatService.saveChat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("get")
    public ResponseEntity<?> getChat(@RequestBody ChatGetRequest request) {
        List<ChatGetResponse> response = chatService.getAllEndChat(request);
        return ResponseEntity.ok(response);
    }
}
