//    package com.dsatrackeradv.dsa_tracker_adv.controller;
//
//    import com.dsatrackeradv.dsa_tracker_adv.entity.User;
//    import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.http.HttpStatus;
//    import org.springframework.http.ResponseEntity;
//    import org.springframework.security.crypto.password.PasswordEncoder;
//    import org.springframework.web.bind.annotation.*;
//
//    @RestController
//    @RequestMapping("/api/auth")
////    @CrossOrigin(origins = "http://localhost:5173")
//    public class AuthController {
//
//        @Autowired
//        private UserRepository userRepository;
//
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//
//        @PostMapping("/signup")
//        public ResponseEntity<User> signup(@RequestBody User user) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            User savedUser = userRepository.save(user);
//            return ResponseEntity.ok(savedUser);
//        }
//
//        @PostMapping("/login")
//        public ResponseEntity<User> login(@RequestBody User loginRequest) {
//            return userRepository.findByUsername(loginRequest.getUsername())
//                    .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
//                    .map(user -> ResponseEntity.ok(user))
//                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
//        }
//    }

package com.dsatrackeradv.dsa_tracker_adv.controller;

import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender; // Add this for email sending

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        // Send welcome email
        sendWelcomeEmail(savedUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
                .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); // Send to user's email
        message.setSubject("Welcome to DSA Tracker!");
        message.setText("Hi " + user.getUsername() + ",\n\n" +
                "🎉 Welcome aboard the DSA Tracker journey!\n\n" +
                "You're all set to sharpen your skills, solve exciting DSA problems, and track your progress like a pro. 💪\n\n" +
                "Every solved problem brings you one step closer to mastery. So let’s get started!\n\n" +
                "🚀 Dive into your first challenge today and make every line of code count.\n\n" +
                "Happy coding!\n\n" +
                "– The DSA Tracker Team\n" +
                "Built with ❤️ by Ritik Raj");

        message.setFrom("vmailai493@gmail.com"); // Replace with your email

        try {
            mailSender.send(message);
            System.out.println("Welcome email sent to " + user.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to " + user.getEmail() + ": " + e.getMessage());
        }
    }
}