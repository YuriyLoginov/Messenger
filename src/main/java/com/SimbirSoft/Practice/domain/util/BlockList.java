package com.SimbirSoft.Practice.domain.util;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "block_list")
@Data
public class BlockList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User UserId;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date startTime;

    @Temporal(TemporalType.TIME)
    private java.util.Date time;
}
