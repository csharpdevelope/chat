package uz.servers.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.servers.chat.model.Chat;
import uz.servers.chat.model.User;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByUsersOrderByCreateAtDesc(User user);
}
