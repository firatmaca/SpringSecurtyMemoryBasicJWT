package com.teamfighttactics.teamfighttactics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroDTO {
    int id;
    private String name;
    private String type;
}
