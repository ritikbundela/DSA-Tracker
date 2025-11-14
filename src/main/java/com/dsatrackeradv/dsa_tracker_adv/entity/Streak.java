package com.dsatrackeradv.dsa_tracker_adv.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "streaks")
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDate date;
    private int count;

    public Streak() {}

    public Streak(Long userId, LocalDate date, int count) {
        this.userId = userId;
        this.date = date;
        this.count = count;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
