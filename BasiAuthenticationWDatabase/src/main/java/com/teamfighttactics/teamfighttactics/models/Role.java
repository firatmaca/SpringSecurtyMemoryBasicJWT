package com.teamfighttactics.teamfighttactics.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MOD("MOD");

    private String value;

    private Role(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
    @Override
    public String getAuthority() {
        return name();
    }
}
