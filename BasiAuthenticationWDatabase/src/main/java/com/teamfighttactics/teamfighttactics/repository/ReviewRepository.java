package com.teamfighttactics.teamfighttactics.repository;

import com.teamfighttactics.teamfighttactics.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByHeroId(int heroId);
}
