package com.teamfighttactics.teamfighttactics.exceptions;

public class HeroNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;
    public HeroNotFoundException(String message){
        super(message);
    }
}
