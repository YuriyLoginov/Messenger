package com.SimbirSoft.Practice.domain.util;

import javax.persistence.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long sender;

    private Long recipient;

    private char message;

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public char getMessage() {
        return message;
    }

    public void setMessage(char message) {
        this.message = message;
    }
}
