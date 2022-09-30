package uz.servers.chat.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import uz.servers.chat.model.MessageType;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MessageRequest {
    @NotNull
    @JsonProperty("chat_id")
    private Long chatId;
    @NotNull
    @JsonProperty("user_id")
    private Long authorId;
    @NotNull
    private MessageType type;
    @NotNull
    private String content;
    private String ext;
}
