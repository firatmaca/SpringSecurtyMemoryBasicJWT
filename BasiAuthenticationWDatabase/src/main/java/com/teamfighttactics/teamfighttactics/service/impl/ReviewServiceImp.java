package com.teamfighttactics.teamfighttactics.service.impl;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.ReviewDto;
import com.teamfighttactics.teamfighttactics.exceptions.HeroNotFoundException;
import com.teamfighttactics.teamfighttactics.exceptions.ReviewNotFoundException;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.models.Review;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import com.teamfighttactics.teamfighttactics.repository.ReviewRepository;
import com.teamfighttactics.teamfighttactics.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImp  implements ReviewService {
    ReviewRepository reviewRepository;
    HeroRepository heroRepository;

    ReviewServiceImp(ReviewRepository reviewRepository,HeroRepository heroRepository){
        this.reviewRepository = reviewRepository;
        this.heroRepository = heroRepository;
    }

    @Override
    public ReviewDto createReview(int heroId,ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Hero hero = heroRepository.findById(heroId).orElseThrow(() ->new HeroNotFoundException("Hero with associated review not found"));
        review.setHero(hero);
        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public List<ReviewDto> getReviewsByHeroId(int id) {
        List<Review> reviews = reviewRepository.findByHeroId(id);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewDto(int reviewId, int heroId) {
        Hero hero = heroRepository.findById(heroId).orElseThrow(()->new HeroNotFoundException("Hero with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with associated hero not found"));
       if(review.getHero().getId() != hero.getId()){
           throw new ReviewNotFoundException("Review  not found");
       }
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int heroId, int reviewId, ReviewDto reviewDto) {
        //Review review = mapToEntity(reviewDto);
        Hero hero = heroRepository.findById(heroId).orElseThrow(() ->new HeroNotFoundException("Hero with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->new ReviewNotFoundException("Review with associated hero not found"));

        if(review.getHero().getId() != hero.getId()){
            throw new ReviewNotFoundException("This review does not belond to a pokemon");
        }
        review.setFirstCategoryName(reviewDto.getFirstCategoryName());
        review.setSecondCategoryName(reviewDto.getSecondCategoryName());
        review.setPrice(reviewDto.getPrice());

        Review updateRevie = reviewRepository.save(review);
        return mapToDto(updateRevie);
    }

    @Override
    public void deleteReview(int heroId, int reviewId) {
        Hero hero = heroRepository.findById(heroId).orElseThrow(() ->new HeroNotFoundException("Hero with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->new ReviewNotFoundException("Review with associated hero not found"));

        if(review.getHero().getId() != hero.getId()){
            throw new ReviewNotFoundException("This review does not belond to a pokemon");
        }
        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setFirstCategoryName(review.getFirstCategoryName());
        reviewDto.setSecondCategoryName(review.getSecondCategoryName());
        reviewDto.setPrice(review.getPrice());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setFirstCategoryName(reviewDto.getFirstCategoryName());
        review.setSecondCategoryName(reviewDto.getSecondCategoryName());
        review.setPrice(reviewDto.getPrice());
        return review;
    }
}
