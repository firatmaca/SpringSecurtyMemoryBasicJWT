package com.teamfighttactics.teamfighttactics.service.impl;

import com.teamfighttactics.teamfighttactics.dto.AuthRequest;
import com.teamfighttactics.teamfighttactics.dto.CreateUserRequest;
import com.teamfighttactics.teamfighttactics.models.User;
import com.teamfighttactics.teamfighttactics.repository.UserRepository;
import com.teamfighttactics.teamfighttactics.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService =jwtService;

    }

    @Override
    public Optional<User> getByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User createUser(CreateUserRequest request) {
        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        return userRepository.save(user);

    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }


    public String genaretTeokenRequest(AuthRequest request){
        return jwtService.genereteToken(request.username());
    }




}
