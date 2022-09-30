package uz.servers.chat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "iamges")
@Getter
@Setter
public class Attachment extends BaseEntity {
    private String filename;
    private String extension;
    private String size;
    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;
}
