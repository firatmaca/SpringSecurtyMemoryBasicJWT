package com.teamfighttactics.teamfighttactics.Repository;

import com.teamfighttactics.teamfighttactics.models.Hero;
import com.teamfighttactics.teamfighttactics.repository.HeroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HeroRepositoryTest {
    @Autowired
    HeroRepository heroRepository;

    @Test
    public void HeroRepository_SaveAll_ReturnSavedHero(){
        Hero hero = Hero.builder()
                .name("myHero")
                .type("defence")
                .build();
        Hero savedHero = heroRepository.save(hero);

        Assertions.assertThat(savedHero).isNotNull();
        Assertions.assertThat(savedHero.getId()).isGreaterThan(0);

    }

    @Test
    public void HeroRepository_GetAll_ReturnMoreThenOneHero() {
        Hero hero = Hero.builder()
                .name("myHero")
                .type("defence")
                .build();
        Hero hero2 = Hero.builder()
                .name("myHero")
                .type("defence")
                .build();

        heroRepository.save(hero);
        heroRepository.save(hero2);

        List<Hero> heroList = heroRepository.findAll();

        Assertions.assertThat(heroList).isNotNull();
        Assertions.assertThat(heroList.size()).isEqualTo(2);
    }

    @Test
    public void HeroRepository_FindById_ReturnHero() {
        Hero hero = Hero.builder()
                .name("myHero")
                .type("defence")
                .build();

        heroRepository.save(hero);

        Hero herofind = heroRepository.findById(hero.getId()).get();

        Assertions.assertThat(herofind).isNotNull();
    }


}
