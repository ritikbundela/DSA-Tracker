package com.dsatrackeradv.dsa_tracker_adv.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Will be hashed by Spring Security

    private String imageUrl;

    private Integer streakCount = 0;
    private LocalDate lastActiveDate;
    private LocalDate lastReminderSent;
    public LocalDate getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(LocalDate lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public LocalDate getLastReminderSent() {
        return lastReminderSent;
    }

    public void setLastReminderSent(LocalDate lastReminderSent) {
        this.lastReminderSent = lastReminderSent;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}