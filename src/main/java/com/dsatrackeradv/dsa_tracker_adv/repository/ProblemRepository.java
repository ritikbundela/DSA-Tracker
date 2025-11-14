package com.dsatrackeradv.dsa_tracker_adv.repository;

import com.dsatrackeradv.dsa_tracker_adv.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByUserId(Long userId);
}