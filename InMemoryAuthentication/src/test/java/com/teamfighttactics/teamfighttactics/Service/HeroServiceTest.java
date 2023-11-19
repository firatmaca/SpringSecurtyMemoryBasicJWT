package com.teamfighttactics.teamfighttactics.Service;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.exceptions.HeroNotFoundException;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import com.teamfighttactics.teamfighttactics.service.impl.HeroServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {
    @Mock
    private HeroRepository heroRepository;
    @InjectMocks
    private HeroServiceImpl heroService;

    @Test
    public void HeroService_CreateHero_RetrunsHeroDto(){
        Hero hero = Hero.builder()
                .name("myhero")
                .type("defence")
                .build();
        HeroDTO heroDTO = HeroDTO.builder()
                .name("myhero")
                .type("defence")
                .build();

        when(heroRepository.save(Mockito.any(Hero.class))).thenReturn(hero);
        HeroDTO savedHero = heroService.createHero(heroDTO);
        Assertions.assertThat(savedHero).isNotNull();
    }

    @Test
    public void HeroService_getAllHero_RetrunsResponseDto(){
        Hero hero = Hero.builder()
                .name("myhero")
                .type("defence")
                .build();
        List<Hero> heroes = Collections.singletonList(hero);
        when(heroRepository.findAll()).thenReturn(heroes);
        List<HeroDTO> getHero = heroService.getAllHero();
        Assertions.assertThat(getHero).isNotNull();
    }

    @Test
    public  void HerOService_FindById_ReturnsHeroDto(){
        int heroId = 1;
        Hero hero = Hero.builder()
                .id(heroId)
                .name("myHero")
                .type("defence")
                .build();
        when(heroRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));
        HeroDTO heroDTO = heroService.getHeroDto(heroId);
        Assertions.assertThat(heroDTO).isNotNull();
    }

    @Test
    public void HeroService_UpdateHero_ReturnsHeroDTO(){
        int heroId = 1;
        HeroDTO heroDTO = HeroDTO.builder().id(heroId).name("myHero").type("defense").build();
        Hero hero = Hero.builder()
                .id(heroId)
                .name("myHero")
                .type("defence")
                .build();
        when(heroRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));

        when(heroRepository.save(Mockito.any(Hero.class))).thenReturn(hero);

        HeroDTO updatedHero = heroService.updateHero(heroDTO,heroId);
        Assertions.assertThat(updatedHero).isNotNull();

        assertEquals(heroId,updatedHero.getId());
    }

    @Test
    public void HeroService_DeleteHeroId_ReturnsVoid(){
        int heroId = 1;
        Hero hero = Hero.builder().id(heroId).name("myHero").type("defense").build();

        when(heroRepository.findById(heroId)).thenReturn(Optional.ofNullable(hero));
       // doNothing().when(heroRepository).delete(hero);
        assertAll(()->heroService.deleteHeroId(heroId));
    }

    @Test
    public  void HerpService_FindById_ReturnsHeroDto_Throws(){
        int heroId = 1;
        when(heroRepository.findById(Mockito.anyInt())).thenThrow(HeroNotFoundException.class);
        assertThrows(HeroNotFoundException.class, () -> heroService.getHeroDto(heroId));
    }


}
