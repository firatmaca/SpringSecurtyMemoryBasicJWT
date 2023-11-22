package com.teamfighttactics.teamfighttactics.service.impl;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.HeroPageResponse;
import com.teamfighttactics.teamfighttactics.exceptions.HeroNotFoundException;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import com.teamfighttactics.teamfighttactics.service.HeroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {

    HeroRepository heroRepository;

    HeroServiceImpl(HeroRepository heroRepository){
        this.heroRepository = heroRepository;
    }
    @Override
    public HeroDTO createHero(HeroDTO heroDTO) {
        Hero hero = new Hero();
        hero.setId(heroDTO.getId());
        hero.setName(heroDTO.getName());
        hero.setType(heroDTO.getType());

        Hero newHero = heroRepository.save(hero);

        HeroDTO heroDTOResponse = new HeroDTO();
        heroDTOResponse.setId(newHero.getId());
        heroDTOResponse.setName(newHero.getName());
        heroDTOResponse.setType(newHero.getType());

        return  heroDTOResponse;
    }

    @Override
    public List<HeroDTO> getAllHero() {
        List<Hero> heroes = heroRepository.findAll();
        return heroes.stream().map(hero -> mapToDto(hero)).collect(Collectors.toList());
    }

    @Override
    public HeroDTO getHeroDto(int id) {
        Hero hero = heroRepository.findById(id).orElseThrow(()-> new HeroNotFoundException("Hero could not be Found"));
        return mapToDto(hero);
    }

    @Override
    public HeroDTO updateHero(HeroDTO heroDTO, int id) {
        Hero hero = heroRepository.findById(id).orElseThrow(()->new HeroNotFoundException("Hero could not be update"));
        hero.setType(heroDTO.getType());
        hero.setName(heroDTO.getName());
        Hero updatedHero = heroRepository.save(hero);
        mapToDto(updatedHero);
        return mapToDto(updatedHero);
    }

    @Override
    public void deleteHeroId(int id) {
        Hero hero = heroRepository.findById(id).orElseThrow(()->new HeroNotFoundException("Hero could not be Delete"));
        heroRepository.deleteById(id);
    }

    @Override
    public HeroPageResponse getAllHero(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Hero> heroes = heroRepository.findAll(pageable);
        List<Hero> heroList = heroes.getContent();
        List<HeroDTO> heroDTOs = heroList.stream().map(hero -> mapToDto(hero)).collect(Collectors.toList());
        HeroPageResponse heroPageResponse = new HeroPageResponse();
        heroPageResponse.setContent(heroDTOs);
        heroPageResponse.setPageSize(heroes.getSize());
        heroPageResponse.setPageNo(heroes.getNumber());
        heroPageResponse.setTotalPages(heroes.getTotalPages());
        heroPageResponse.setTotalElements(heroes.getTotalElements());
        heroPageResponse.setLast(heroes.isLast());

        return heroPageResponse;
    }

    private HeroDTO mapToDto(Hero hero){
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setId(hero.getId());
        heroDTO.setName(hero.getName());
        heroDTO.setType(hero.getType());
        return heroDTO;
    }

    private Hero mapToEntity(HeroDTO heroDTO){
        Hero hero = new Hero();
        hero.setId(heroDTO.getId());
        hero.setName(heroDTO.getName());
        hero.setType(heroDTO.getType());
        return hero;
    }
}
