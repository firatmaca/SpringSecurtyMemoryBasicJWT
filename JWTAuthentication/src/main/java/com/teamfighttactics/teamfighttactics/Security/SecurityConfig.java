package com.teamfighttactics.teamfighttactics.Security;

import com.teamfighttactics.teamfighttactics.models.Role;
import com.teamfighttactics.teamfighttactics.service.UserService;
import com.teamfighttactics.teamfighttactics.service.impl.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailServiceImp userDetailServiceImp;
    private  final  PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter , UserDetailServiceImp userDetailServiceImp, PasswordEncoder passwordEncoder){
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailServiceImp =userDetailServiceImp;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security ) throws Exception{
        security
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(x->
                        x.requestMatchers( "/auth/addnewuser/**","/auth/generateToken/**","/auth/welcome/**").permitAll()
                )
                .authorizeHttpRequests(x->
                        x.requestMatchers("/auth/user/**").hasRole("USER")
                        .requestMatchers("/auth/admin/**").hasRole("ADMIN")
                )
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                . authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailServiceImp);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }





}
