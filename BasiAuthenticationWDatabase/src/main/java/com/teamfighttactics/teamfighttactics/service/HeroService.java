package com.teamfighttactics.teamfighttactics.service;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.HeroPageResponse;

import java.util.List;

public interface HeroService {

    HeroDTO createHero(HeroDTO heroDTO);

    List<HeroDTO> getAllHero();

    HeroDTO getHeroDto(int id);

    HeroDTO updateHero(HeroDTO heroDTO,int id);

    void deleteHeroId(int id);

    HeroPageResponse getAllHero(int pageNo, int pageSize);
}
