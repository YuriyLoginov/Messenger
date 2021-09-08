package com.SimbirSoft.Practice.domain.util;


import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long creator;

    private String name;

    private Long users;

    private Long message;
}
