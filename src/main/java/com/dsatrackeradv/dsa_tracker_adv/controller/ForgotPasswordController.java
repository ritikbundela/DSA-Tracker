package com.dsatrackeradv.dsa_tracker_adv.controller;

import com.dsatrackeradv.dsa_tracker_adv.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth") // Aligned with your existing auth endpoints
public class ForgotPasswordController {

    @Value("${frontend.forgot-pass}")
    private String resetBaseUrl;


    private final PasswordResetService passwordResetService;
    private final JavaMailSender mailSender;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        String token = passwordResetService.createPasswordResetToken(email);
        String resetLink = resetBaseUrl + "/reset-password?token=" + token;


        sendResetEmail(email, resetLink);

        return "Password reset link sent to " + email;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return "Password has been reset successfully.";
    }
//    vishaljainvj3148@gmail.com
//    vishaljainvj3148@gmail.com

    private void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request - DSA Tracker");
        message.setText("Hi,\n\n" +
                "You requested a password reset for your DSA Tracker account.\n" +
                "Click the link below to reset your password (valid for 1 hour):\n" +
                resetLink + "\n\n" +
                "If you didn’t request this, ignore this email.\n\n" +
                "Happy coding,\nDSA Tracker Team");
        message.setFrom("vmailai493@gmail.com"); // Your sender email

        try {
            mailSender.send(message);
            System.out.println("Password reset email sent to " + email);
        } catch (Exception e) {
            System.err.println("Failed to send reset email to " + email + ": " + e.getMessage());
            throw new RuntimeException("Failed to send reset email: " + e.getMessage());
        }
    }
}