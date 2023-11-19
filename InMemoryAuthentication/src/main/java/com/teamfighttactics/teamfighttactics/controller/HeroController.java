package com.teamfighttactics.teamfighttactics.controller;

import com.teamfighttactics.teamfighttactics.dto.HeroDTO;
import com.teamfighttactics.teamfighttactics.dto.HeroPageResponse;
import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class HeroController {
    private HeroService heroService;

    @Autowired
    HeroController(HeroService heroService){
        this.heroService = heroService;
    }

    @GetMapping("hero")
    public ResponseEntity<List<HeroDTO>> getHeros(){
        return  ResponseEntity.ok(heroService.getAllHero());
    }

    @GetMapping("heroPage")
    public ResponseEntity<HeroPageResponse> getHerosPage(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize) {

        return  new ResponseEntity<>(heroService.getAllHero(pageNo,pageSize),HttpStatus.OK);
    }

    @GetMapping("hero/{id}")
    public ResponseEntity<HeroDTO> getHeroDetail(@PathVariable(value = "id") int id){
        return ResponseEntity.ok(heroService.getHeroDto(id));
    }


    @PostMapping("/hero/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HeroDTO> createHero(@RequestBody HeroDTO heroDTO){
        return new ResponseEntity<>(heroService.createHero(heroDTO),HttpStatus.CREATED);
    }

    @PutMapping("/hero/{id}/update")
    public ResponseEntity<HeroDTO> updateHero(@RequestBody HeroDTO heroDTO , @PathVariable(value = "id") int heroId ){
        HeroDTO response =heroService.updateHero(heroDTO,heroId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/hero/{id}/delete")
    public ResponseEntity<String> deleteHero(@PathVariable(value = "id") int heroId ){
        heroService.deleteHeroId(heroId);
        return new  ResponseEntity<>("Hero deleted",HttpStatus.OK);
    }

}
