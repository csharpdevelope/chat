package uz.servers.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToOne
    private Attachment image;
}
