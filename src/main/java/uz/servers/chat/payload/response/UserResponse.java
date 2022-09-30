package uz.servers.chat.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;

    public UserResponse(Long id) {
        this.id = id;
    }

    public UserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
