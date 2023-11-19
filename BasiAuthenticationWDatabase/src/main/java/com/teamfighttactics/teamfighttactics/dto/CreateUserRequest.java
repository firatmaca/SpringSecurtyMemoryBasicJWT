package com.teamfighttactics.teamfighttactics.dto;

import com.teamfighttactics.teamfighttactics.models.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
        ) {
}
