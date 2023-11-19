package com.teamfighttactics.teamfighttactics.service.impl;

import com.teamfighttactics.teamfighttactics.dto.CreateUserRequest;
import com.teamfighttactics.teamfighttactics.models.User;
import com.teamfighttactics.teamfighttactics.repository.UserRepository;
import com.teamfighttactics.teamfighttactics.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository ,BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getByUserName(String userName){
        return userRepository.findByUsername(userName);
    }

    @Override
    public User createUser(CreateUserRequest request){
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
}
