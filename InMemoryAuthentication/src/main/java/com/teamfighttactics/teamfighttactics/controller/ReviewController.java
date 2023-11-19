package com.teamfighttactics.teamfighttactics.controller;

import com.teamfighttactics.teamfighttactics.dto.ReviewDto;
import com.teamfighttactics.teamfighttactics.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/")
public class ReviewController {
    ReviewService reviewService;
    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/hero/{heroId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "heroId") int heroId, @RequestBody ReviewDto reviewDto){
        return  new ResponseEntity<>(reviewService.createReview(heroId,reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("hero/{heroId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByHeroId(@PathVariable(value = "heroId") int heroId){
        return  new ResponseEntity<>(reviewService.getReviewsByHeroId(heroId), HttpStatus.OK);
    }
    @GetMapping("hero/{heroId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(
            @PathVariable(value = "heroId") int heroId ,@PathVariable(value = "reviewId") int reviewId){
        return  new ResponseEntity<>(reviewService.getReviewDto(heroId,reviewId), HttpStatus.OK);
    }

    @PutMapping("hero/{heroId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(
            @PathVariable(value = "heroId") int heroId ,@PathVariable(value = "reviewId") int reviewId, @RequestBody ReviewDto reviewDto){
        return  new ResponseEntity<>(reviewService.updateReview(heroId,reviewId,reviewDto), HttpStatus.OK);
    }

    @DeleteMapping("hero/{heroId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable(value = "heroId") int heroId ,@PathVariable(value = "reviewId") int reviewId){
        reviewService.deleteReview(heroId,reviewId);
        return  new ResponseEntity<>("Review successfully deleted", HttpStatus.OK);
    }
}
