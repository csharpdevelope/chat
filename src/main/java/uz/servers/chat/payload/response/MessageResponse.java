package uz.servers.chat.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private Long id;

    public MessageResponse(Long id) {
        this.id = id;
    }
}
