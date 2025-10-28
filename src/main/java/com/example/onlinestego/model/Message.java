package com.example.onlinestego.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="messagesdb")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;

    private String imagePath;
    private LocalDateTime sentAt;

    public Message() {}

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public User getSender(){ return sender; }
    public void setSender(User sender){ this.sender = sender; }
    public User getReceiver(){ return receiver; }
    public void setReceiver(User receiver){ this.receiver = receiver; }
    public String getImagePath(){ return imagePath; }
    public void setImagePath(String imagePath){ this.imagePath = imagePath; }
    public LocalDateTime getSentAt(){ return sentAt; }
    public void setSentAt(LocalDateTime sentAt){ this.sentAt = sentAt; }
}
