package com.teamfighttactics.teamfighttactics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class Public {

    @GetMapping()
    public String getPublic(){
        return "user froma public";
    }
}
