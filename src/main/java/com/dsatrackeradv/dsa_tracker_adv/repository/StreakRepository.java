package com.dsatrackeradv.dsa_tracker_adv.repository;

import com.dsatrackeradv.dsa_tracker_adv.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {
    List<Streak> findByUserId(Long userId);
    Optional<Streak> findByUserIdAndDate(Long userId, LocalDate date);
}
