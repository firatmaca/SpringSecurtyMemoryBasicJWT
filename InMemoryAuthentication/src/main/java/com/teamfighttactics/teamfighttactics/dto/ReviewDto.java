package com.teamfighttactics.teamfighttactics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {
    private int id;
    private String firstCategoryName;
    private String secondCategoryName;
    private int price;
}
