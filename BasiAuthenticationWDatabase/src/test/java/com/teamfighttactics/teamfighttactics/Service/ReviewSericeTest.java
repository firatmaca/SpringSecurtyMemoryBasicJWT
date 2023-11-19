package com.teamfighttactics.teamfighttactics.Service;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.ReviewDto;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.models.Review;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import com.teamfighttactics.teamfighttactics.repository.ReviewRepository;
import com.teamfighttactics.teamfighttactics.service.impl.ReviewServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewSericeTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private HeroRepository herorRepository;

    @InjectMocks
    private ReviewServiceImp reviewServiceImp;

    Hero hero;
    Review review;
    HeroDTO heroDTO;
    ReviewDto reviewDto;

    @BeforeEach
    private void init(){
        System.out.println("BeforeEach");
        hero = Hero.builder()
                .id(1)
                .name("myhero")
                .type("defence")
                .build();

        heroDTO = HeroDTO.builder()
                .id(1)
                .name("myhero")
                .type("defence")
                .build();

        review = Review.builder()
                .id(1)
                .firstCategoryName("kavgaci")
                .secondCategoryName("ezerGecer")
                .price(5)
                .build();
        reviewDto = ReviewDto.builder()
                .id(1)
                .firstCategoryName("kavgaci")
                .secondCategoryName("ezerGecer")
                .price(5)
                .build();
    }

    @Test
    public void ReviewService_CreateReview_RetrunReviewDto(){
        when(herorRepository.findById(hero.getId())).thenReturn(Optional.ofNullable(hero));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        ReviewDto savedReview =  reviewServiceImp.createReview(hero.getId(),reviewDto);
        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewsByHeroId_RetrunListReviewDto(){
        int reviewId = 1;
        when(reviewRepository.findByHeroId(reviewId)).thenReturn(Arrays.asList(review));
        List<ReviewDto> pokemonReturn = reviewServiceImp.getReviewsByHeroId(reviewId);
        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewDto_RetrunReviewDto(){
        int heroId = 1;
        int reviewId = 1;
        review.setHero(hero);
        when(herorRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        ReviewDto getReview = reviewServiceImp.getReviewDto(reviewId,heroId);
        Assertions.assertThat(getReview).isNotNull();
        assertEquals(review.getHero().getId(),getReview.getId());
    }

    @Test
    public void ReviewService_updateReview_RetrunReviewDto(){
        int heroId = 1;
        int reviewId = 1;
        reviewDto.setFirstCategoryName("update");
        hero.setReviews(Arrays.asList(review));
        review.setHero(hero);
        when(herorRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        ReviewDto updateReturn = reviewServiceImp.updateReview(heroId, reviewId, reviewDto);
        assertEquals(updateReturn.getFirstCategoryName(),reviewDto.getFirstCategoryName());
        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void ReviewService_DeleteReview_RetrunReviewDto(){
        int heroId = 1;
        int reviewId = 1;
        hero.setReviews(Arrays.asList(review));
        review.setHero(hero);
        when(herorRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        assertAll(() -> reviewServiceImp.deleteReview(heroId, reviewId));
    }

}