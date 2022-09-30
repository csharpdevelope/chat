package uz.servers.chat.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatRequest {
    private String name;
    private List<Long> users;
}
