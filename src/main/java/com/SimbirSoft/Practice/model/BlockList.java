package com.SimbirSoft.Practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "block_list")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
