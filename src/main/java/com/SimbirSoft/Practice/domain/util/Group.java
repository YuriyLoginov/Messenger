package com.SimbirSoft.Practice.domain.util;


import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    private String name;

    @ManyToOne
    @JoinColumn(name = "users")
    private User users;

    @ManyToOne
    @JoinColumn(name = "message")
    private Message message;
}
