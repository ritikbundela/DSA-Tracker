package com.dsatrackeradv.dsa_tracker_adv.scheduler;

import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class ReminderScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
//    @Scheduled(cron = "0 * * * * ?") // every minute

    public void sendReminders() {
        List<User> users = userRepository.findAll();
        LocalDate today = LocalDate.now();

        for (User user : users) {
            //This includes new users or inactive users who never solved a problem.
            if ((user.getLastActiveDate() == null || today.minusDays(1).isAfter(user.getLastActiveDate())) &&
                    (user.getLastReminderSent() == null || today.isAfter(user.getLastReminderSent()))) {

                sendReminderEmail(user);
                user.setLastReminderSent(today);
                userRepository.save(user);
            }
        }
    }

    private void sendReminderEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); // Send to user's email
        message.setSubject("DSA Tracker Reminder");
        message.setText("Hi " + user.getUsername() + ",\n\n" +
                "Just a friendly nudge! 🚀 It seems you haven't solved a DSA problem recently.\n\n" +
                "Keep your momentum going and don't let your streak break — even one problem today can make a difference!\n\n" +
                "💡 Tip: Consistency beats intensity. You're building something great.\n\n" +
                "Happy coding!\n\n" +
                "– The DSA Tracker Team\n" +
                "Owned and built with ❤️ by Ritik Raj");
        message.setFrom("vmailai493@gmail.com"); // Replace with your email

        try {
            mailSender.send(message);
            System.out.println("Reminder email sent to " + user.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email to " + user.getEmail() + ": " + e.getMessage());
        }
    }
}