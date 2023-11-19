package com.teamfighttactics.teamfighttactics.service;

import com.teamfighttactics.teamfighttactics.dto.CreateUserRequest;
import com.teamfighttactics.teamfighttactics.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUserName(String userName);
    User createUser(CreateUserRequest request);

    void deleteAll();
}
