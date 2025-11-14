package com.dsatrackeradv.dsa_tracker_adv.controller;

import com.dsatrackeradv.dsa_tracker_adv.entity.Streak;
import com.dsatrackeradv.dsa_tracker_adv.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/streaks")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @PostMapping("/update/{userId}")
    public void updateStreak(@PathVariable Long userId) {
        streakService.updateStreak(userId);
    }

    @GetMapping("/{userId}")
    public List<Streak> getUserStreaks(@PathVariable Long userId) {
        return streakService.getUserStreaks(userId);
    }
}
