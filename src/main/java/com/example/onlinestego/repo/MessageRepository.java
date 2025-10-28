package com.example.onlinestego.repo;

import com.example.onlinestego.model.Message;
import com.example.onlinestego.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverOrderBySentAtDesc(User receiver);
    List<Message> findBySenderAndReceiverOrderBySentAtAsc(User sender, User receiver);
}
