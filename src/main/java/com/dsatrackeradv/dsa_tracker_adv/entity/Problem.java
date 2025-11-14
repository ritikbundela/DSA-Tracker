    package com.dsatrackeradv.dsa_tracker_adv.entity;

    import lombok.Data;
    import jakarta.persistence.*;
    import java.time.LocalDateTime;

    @Entity
    @Data
    public class Problem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Long userId; // Foreign key to User

        @Column(nullable = false)
        private String title;

        private String link;
        private String topic;
        private String difficulty; // e.g., "easy", "medium", "hard"
        private String status = "unsolved"; // Default value

        @Column(updatable = false)
        private LocalDateTime createdAt;

        private LocalDateTime lastRevisedAt;

        @PrePersist
        public void prePersist() {
            this.createdAt = LocalDateTime.now();
        }
    }