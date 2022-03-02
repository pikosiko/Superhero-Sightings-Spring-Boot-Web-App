package com.sg.superheroes.dao;

import com.sg.superheroes.entities.Hero;
import com.sg.superheroes.entities.Location;
import com.sg.superheroes.entities.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class HeroDaoDB {

    @Autowired
    JdbcTemplate jdbc;


    //GET Hero FOR A GIVEN ID
    public Hero getHeroByID(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM Hero WHERE hero_id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            hero.setOrganisations(getOrgsForHero(id));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    //GET ALL ORGS FOR A HERO
    public List<Organisation> getOrgsForHero(int id) {
        final String SELECT_ORGS_FOR_HERO = "SELECT o.* FROM Organisation o "
                + "JOIN HeroAtOrg ho ON ho.org_id = o.org_id WHERE ho.hero_id = ?";
        return jdbc.query(SELECT_ORGS_FOR_HERO, new OrganisationDaoDB.OrganisationMapper(), id);
    }

    //GET ALL Heroes
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM Hero";
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
        return heroes;
    }

    //ADD HERO METHOD
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(heroName, heroDescription, superpower) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHero_id(newId);
        return hero;
    }



    //UPDATE THE HERO
    @Transactional
    public void updateHero(Hero hero) {
        final String UPDATE_COURSE = "UPDATE Hero SET heroName = ?, heroDescription = ?, "
                + "superpower = ? WHERE hero_id = ?";
        jdbc.update(UPDATE_COURSE,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getSuperpower(),
                hero.getHero_id());

    }


    //DELETE AN HERO
    @Transactional
    public void deleteHero(int id) {
        final String DELETE_HERO_FROM_ORG = "DELETE FROM HeroAtOrg WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_FROM_ORG, id);

        final String DELETE_HERO_FROM_SIGHTING = "DELETE FROM Sighting WHERE hero_id = ?";
        jdbc.update(DELETE_HERO_FROM_ORG, id);

        final String DELETE_HERO = "DELETE FROM Hero WHERE hero_id = ?";
        jdbc.update(DELETE_HERO, id);
    }









    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHero_id(rs.getInt("hero_id"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setHeroDescription(rs.getString("heroDescription"));
            hero.setSuperpower(rs.getString("superpower"));


            return hero;
        }
    }

}
