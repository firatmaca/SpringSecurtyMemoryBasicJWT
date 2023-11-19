package com.teamfighttactics.teamfighttactics.service;

import com.teamfighttactics.teamfighttactics.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int heroId,ReviewDto reviewDto);

    List<ReviewDto> getReviewsByHeroId(int id);

    ReviewDto getReviewDto(int reviewId,int heroId);

    ReviewDto updateReview(int heroId,int reviewId ,ReviewDto reviewDto);

    void deleteReview(int heroId,int reviewId);
}
