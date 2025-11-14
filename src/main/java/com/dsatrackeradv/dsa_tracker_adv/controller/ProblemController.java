package com.dsatrackeradv.dsa_tracker_adv.controller;

import com.dsatrackeradv.dsa_tracker_adv.entity.Problem;
import com.dsatrackeradv.dsa_tracker_adv.service.GeminiService;
import com.dsatrackeradv.dsa_tracker_adv.service.StreakService;
import com.dsatrackeradv.dsa_tracker_adv.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
//@CrossOrigin(origins = "http://localhost:5173")
public class ProblemController {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private StreakService streakService;

    @PostMapping
    public ResponseEntity<Problem> addProblem(@RequestBody Problem problem) {
        Problem savedProblem = problemRepository.save(problem);
        // Update streak when a problem is added
        streakService.updateStreak(problem.getUserId());
        return ResponseEntity.ok(savedProblem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Problem>> getProblemsByUser(@PathVariable Long userId) {
        List<Problem> problems = problemRepository.findByUserId(userId);
        return ResponseEntity.ok(problems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problem> updateProblem(@PathVariable Long id, @RequestBody Problem problemDetails) {
        return problemRepository.findById(id)
                .map(problem -> {
                    problem.setTitle(problemDetails.getTitle());
                    problem.setLink(problemDetails.getLink());
                    problem.setTopic(problemDetails.getTopic());
                    problem.setDifficulty(problemDetails.getDifficulty());
                    problem.setStatus(problemDetails.getStatus());
                    problem.setLastRevisedAt(LocalDateTime.now());
                    Problem updatedProblem = problemRepository.save(problem);
                    // Update streak when a problem is updated
                    streakService.updateStreak(problem.getUserId());
                    return ResponseEntity.ok(updatedProblem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        return problemRepository.findById(id)
                .map(problem -> {
                    Long userId = problem.getUserId();
                    problemRepository.delete(problem);
                    // Update streak when a problem is deleted (optional)
                    streakService.updateStreak(userId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/similar")
    @Cacheable("similarProblems")
    public ResponseEntity<List<String>> getSimilarProblems(@PathVariable Long id) {
        return problemRepository.findById(id)
                .map(problem -> {
                    List<String> similarProblems = geminiService.getSimilarProblems(problem.getTitle(), problem.getTopic());
                    return ResponseEntity.ok(similarProblems);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    // New endpoint to manually update/check streak
//    @GetMapping("/users/{id}/streak")
//    public ResponseEntity<Integer> getUpdateStreak(@PathVariable Long id) {
//        Integer streakCount = streakService.updateStreak(id);
//        return ResponseEntity.ok(streakCount);
//    }
}