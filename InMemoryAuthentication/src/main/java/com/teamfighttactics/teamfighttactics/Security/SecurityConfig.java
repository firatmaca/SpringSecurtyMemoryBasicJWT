package com.teamfighttactics.teamfighttactics.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(){
        UserDetails admin = User.builder()
                .username("firat")
                .password(passwordEncoder().encode("pass"))
                .roles("ADMIN")
                .build();
        UserDetails user1 = User.builder()
                .username("other")
                .password(passwordEncoder().encode("pass"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user1);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
        security
                .headers(x-> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x->x.requestMatchers("/api/hero/**").permitAll())
                .authorizeHttpRequests(x->x.requestMatchers("/api/**").hasRole("ADMIN"))
                .authorizeHttpRequests(x->x.requestMatchers("/api/hero/**").hasRole("USER"))
                .authorizeHttpRequests(x-> x.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return security.build();
    }

}
