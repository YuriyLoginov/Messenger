package com.SimbirSoft.Practice.domain.util;

import com.SimbirSoft.Practice.domain.util.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Chat> chatsSend;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    private List<Chat> chatsRec;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "UserId")
    private List<BlockList> bloc;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean valid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Message> message;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Group> creatorGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Group> userGroup;
}
