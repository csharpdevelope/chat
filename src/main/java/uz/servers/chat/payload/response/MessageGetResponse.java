package uz.servers.chat.payload.response;

import lombok.Getter;
import lombok.Setter;
import uz.servers.chat.model.MessageType;

import java.util.Date;

@Getter
@Setter
public class MessageGetResponse {
    private Long id;
    private Date createAt;
    private MessageType type;
    private String content;
    private String url;
}
