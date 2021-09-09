package com.SimbirSoft.Practice.domain.util;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
    private List<Chat> chats;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
    private List<Group> groups;
}