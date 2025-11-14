package com.dsatrackeradv.dsa_tracker_adv.service;

import com.dsatrackeradv.dsa_tracker_adv.entity.PasswordResetToken;
import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.PasswordResetTokenRepository;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder; // Added for encoding new password

    public String createPasswordResetToken(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with this email.");
        }

        User user = optionalUser.get();

        // Check if a token already exists for this user and delete it
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        return token;
    }

    public boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken); // Clean up expired token
            throw new RuntimeException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Encode the new password
        userRepository.save(user);
        tokenRepository.delete(resetToken); // Delete token after successful reset
    }
}