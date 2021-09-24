package com.SimbirSoft.Practice.model;

import com.SimbirSoft.Practice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "UserId")
    private List<BlockList> bloc;

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Message> message;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Room> rooms;

}
