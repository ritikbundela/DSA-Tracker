package com.dsatrackeradv.dsa_tracker_adv.scheduler;

import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import com.dsatrackeradv.dsa_tracker_adv.service.StreakService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StreakScheduler {

    private final StreakService streakService;
    private final UserRepository userRepository;

    public StreakScheduler(StreakService streakService, UserRepository userRepository) {
        this.streakService = streakService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void updateDailyStreaks() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            streakService.updateStreak(user.getId());
        }
    }
}
