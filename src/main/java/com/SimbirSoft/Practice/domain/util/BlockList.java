package com.SimbirSoft.Practice.domain.util;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "block_list")
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

    public BlockList() {
    }

    public User getUserId() {
        return UserId;
    }

    public void setUserId(User userId) {
        UserId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
