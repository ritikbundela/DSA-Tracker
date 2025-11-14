package com.dsatrackeradv.dsa_tracker_adv.service;

import com.dsatrackeradv.dsa_tracker_adv.entity.Streak;
import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.StreakRepository;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private UserRepository userRepository;


    public void updateStreak(Long userId) {
        LocalDate today = LocalDate.now();
        Optional<Streak> existingStreak = streakRepository.findByUserIdAndDate(userId, today);

        if (existingStreak.isPresent()) {
            // User already solved a problem today, just update lastActiveDate
            updateUserLastActiveDate(userId, today);
            return;
        }

        // Check yesterday's streak
        Optional<Streak> yesterdayStreak = streakRepository.findByUserIdAndDate(userId, today.minusDays(1));
        int newCount = yesterdayStreak.map(Streak::getCount).orElse(0) + 1;

        // Save new streak
        Streak streak = new Streak(userId, today, newCount);
        streakRepository.save(streak);

        // Update user's last active date and streak count
        updateUserLastActiveDate(userId, today);
        updateUserStreakCount(userId, newCount);
    }

//    public void updateStreak(Long userId) {
//        LocalDate today = LocalDate.now();
//
//        // If user has already solved a problem today, do nothing
//        Optional<Streak> existingStreak = streakRepository.findByUserIdAndDate(userId, today);
//        if (existingStreak.isPresent()) {
//            updateUserLastActiveDate(userId, today);
//            return;
//        }
//
//        // User didn't solve today, reset streak to 0
//        Streak streak = new Streak(userId, today, 0);
//        streakRepository.save(streak);
//
//        // Reset user's streak count to 0
//        updateUserLastActiveDate(userId, today);
//        updateUserStreakCount(userId, 0);
//    }
//

    private void updateUserLastActiveDate(Long userId, LocalDate date) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setLastActiveDate(date);
            userRepository.save(user);
        });
    }

    private void updateUserStreakCount(Long userId, int count) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setStreakCount(count);
            userRepository.save(user);
        });
    }

    public List<Streak> getUserStreaks(Long userId) {
        return streakRepository.findByUserId(userId);
    }
}


