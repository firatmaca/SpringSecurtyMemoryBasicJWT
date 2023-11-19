package com.teamfighttactics.teamfighttactics.repository;

import com.teamfighttactics.teamfighttactics.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero,Integer > {
}
