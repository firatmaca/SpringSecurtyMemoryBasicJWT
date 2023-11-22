package com.teamfighttactics.teamfighttactics.Security;

import com.teamfighttactics.teamfighttactics.service.impl.JWTService;
import com.teamfighttactics.teamfighttactics.service.impl.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    @Autowired
    private final UserDetailsService userDetailServiceImp;
    public JwtAuthFilter(JWTService jwtService, UserDetailsService userDetailServiceImp){
        this.jwtService = jwtService;
        this.userDetailServiceImp = userDetailServiceImp;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName =jwtService.extractUsernameToken(token);
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user  = userDetailServiceImp.loadUserByUsername(userName);
            if(jwtService.tokenValidate(token,user)){
                UsernamePasswordAuthenticationToken autToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                autToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
