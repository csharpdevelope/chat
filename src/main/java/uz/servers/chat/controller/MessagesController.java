package uz.servers.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.servers.chat.model.MessageType;
import uz.servers.chat.payload.request.MessageGetRequest;
import uz.servers.chat.payload.request.MessageRequest;
import uz.servers.chat.payload.response.MessageGetResponse;
import uz.servers.chat.payload.response.MessageResponse;
import uz.servers.chat.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessagesController {
    private final MessageService messageService;

    @PostMapping("add")
    public ResponseEntity<?> addMessage(@RequestBody MessageRequest request) {
        MessageResponse response = null;
        if (request.getType() == MessageType.TEXT)
            response = messageService.addTextMessage(request);
        else
            response = messageService.addImageMessage(request);
        if (response == null) {
            return ResponseEntity.internalServerError().body("Server error");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("get")
    public ResponseEntity<?> getMessage(@RequestBody MessageGetRequest request) {
        List<MessageGetResponse> responses = messageService.getAllMessage(request);
        return ResponseEntity.ok(responses);
    }
}
