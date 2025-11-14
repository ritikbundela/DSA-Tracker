package com.dsatrackeradv.dsa_tracker_adv.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dsatrackeradv.dsa_tracker_adv.entity.User;
import com.dsatrackeradv.dsa_tracker_adv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/upload")
    public ResponseEntity<String> saveImageUrl(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf(payload.get("userId").toString());
        String imageUrl = payload.get("imageUrl").toString();

        User user = userRepo.findById(userId).orElseThrow();
        user.setImageUrl(imageUrl);
        userRepo.save(user);

        return ResponseEntity.ok("Image URL saved successfully");
    }

}
