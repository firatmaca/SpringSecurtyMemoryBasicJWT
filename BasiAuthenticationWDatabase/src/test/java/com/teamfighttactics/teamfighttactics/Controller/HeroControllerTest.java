package com.teamfighttactics.teamfighttactics.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamfighttactics.teamfighttactics.controller.HeroController;
import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.HeroPageResponse;
import com.teamfighttactics.teamfighttactics.dto.ReviewDto;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.models.Review;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import com.teamfighttactics.teamfighttactics.service.HeroService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = HeroController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService heroService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void HeroController_CreateHero_RetrunCreated() throws Exception{
        given(heroService.createHero(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/hero/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(heroDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(heroDTO.getType())));

    }

    @Test
    public void HeroController_getHerosPage_ReturnResponseDto() throws Exception {
        HeroPageResponse responseDto = HeroPageResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(heroDTO)).build();
        when(heroService.getAllHero(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/heroPage")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void HeroController_GetAllHero_ReturnResponseDto() throws Exception {
        when(heroService.getAllHero()).thenReturn(Arrays.asList(heroDTO));
        ResultActions response = mockMvc.perform(get("/api/hero")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void HeroController_GetHeroDetail_ReturnResponseDto() throws Exception {
        int heroId = 1;
        when(heroService.getHeroDto(heroId)).thenReturn(heroDTO);
        ResultActions response = mockMvc.perform(get("/api/hero/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(heroDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(heroDTO.getType())));
    }

    @Test
    public void HeroController_UpdateHero_ReturnResponseDto() throws Exception {
        int heroId = 1;
        when(heroService.updateHero(heroDTO,heroId)).thenReturn(heroDTO);

        ResultActions response = mockMvc.perform(put("/api/hero/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(heroDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(heroDTO.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(heroDTO.getType())));

    }

    @Test
    public void HeroController_DeleteHero_ReturnResponseDto() throws Exception {
        int heroId = 1;
        doNothing().when(heroService).deleteHeroId(heroId);
        ResultActions response = mockMvc.perform(delete("/api/hero/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


}
