package uz.servers.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.servers.chat.model.Chat;
import uz.servers.chat.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatOrderByCreateAt(Chat chat);
}
