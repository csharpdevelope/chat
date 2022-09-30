package uz.servers.chat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ChatGetResponse {
    private Long id;
    private String name;
    private Date createAt;
}
