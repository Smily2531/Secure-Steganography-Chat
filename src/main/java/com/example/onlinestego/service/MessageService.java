package com.example.onlinestego.service;

import com.example.onlinestego.model.Message;
import com.example.onlinestego.model.User;
import com.example.onlinestego.repo.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repo;
    public MessageService(MessageRepository repo){ this.repo = repo; }
    public Message save(Message m){ return repo.save(m); }
    public List<Message> inbox(User receiver){ return repo.findByReceiverOrderBySentAtDesc(receiver); }
    public List<Message> conversation(User a, User b){ return repo.findBySenderAndReceiverOrderBySentAtAsc(a,b); }
}
