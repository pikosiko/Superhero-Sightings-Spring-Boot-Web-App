package com.sg.superheroes.dao;

import com.sg.superheroes.entities.Hero;
import com.sg.superheroes.entities.Location;
import com.sg.superheroes.entities.Organisation;
import com.sg.superheroes.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class SightingDaoDB {



    @Autowired
    JdbcTemplate jdbc;


    //GETS ALL HEROES SIGHTED AT A LOCATION   !!!TASK1!!!
    public List<Hero> getHeroSightingsForLocation(int loc_id) {
        try {
            final String GET_HERO_SIGHTINGS_FOR_LOCATION = "SELECT h.* FROM Hero h " + "JOIN sighting s ON s.hero_id = h.hero_id WHERE s.location_id = ?";

            //Gets all sightings and associates them with Heroes and Locations
            final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
            associateSightingsHeroesLocations(sightings);

            //Once that connection is made, we get a hero list and return it
            List<Hero> heroes =  jdbc.query(GET_HERO_SIGHTINGS_FOR_LOCATION, new HeroDaoDB.HeroMapper(), loc_id);
            return  heroes;
        }
        catch(DataAccessException ex) {
            return null;
        }
    }


    //Insert a Date AND get hero names and locations that they have been sighted  !!!TASK2!!!
    public List<Sighting> getSightingsForDate(Date date){
        try {
            //WE GET ALL SIGHTINGS FOR A DATE
            final String SELECT_ALL_SIGHTINGS_WITH_DATE = "SELECT * FROM Sighting WHERE s.dateT =?";
            List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS_WITH_DATE, new SightingMapper());
            associateSightingsHeroesLocations(sightings);

            //THEN AFTER WE MADE AN ASSOCIATION WE SELECT THE HERO AND LOCATION NAME FROM THAT SIGHTING FOR THAT DATE AND WE RETURN IT
            final String GET_SIGHTINGS_FOR_DATE = "SELECT location_name,heroName FROM sighting s " +
                                                    "JOIN hero h ON s.hero_id=h.hero_id JOIN location l ON s.location_id=l.location_id " +
                                                    "WHERE s.dateT =?";
            return jdbc.query(GET_SIGHTINGS_FOR_DATE, new SightingDaoDB.SightingMapper(), date);
        }
        catch(DataAccessException ex) {
            return null;
        }
    }


    //GET Location FOR A GIVEN ID
    public Sighting getSightingByID(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sighting_id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getSightingLocation(id));
            sighting.setHero(getSightingHero(id));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    //HELPER METHOD THAT GETS A SIGHTING LOCATION
    private Location getSightingLocation(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.location_id = l.location_id WHERE s.sighting_id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
    }
    //HELPER METHOD THAT GETS A SIGHTING HERO
    private Hero getSightingHero(int id) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.hero_id = h.hero_id WHERE s.sighting_id = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroDaoDB.HeroMapper(), id);
    }


    //GET ALL SIGHTINGS
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSightingsHeroesLocations(sightings);
        return sightings;
    }

    //HELPER METHOD THAT SETS RELATIONSHIPS WITH SIGHTING HERO LOCATION
    private void associateSightingsHeroesLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setHero(getSightingHero(sighting.getSightingID()));
            sighting.setLocation(getSightingLocation(sighting.getSightingID()));
        }
    }




    //ADD SIGHTING METHOD
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_ORG = "INSERT INTO Sighting(hero_id, location_id, dateT) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORG,
                sighting.getHeroID(),
                sighting.getLocationID(),
                sighting.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newId);
        return sighting;
    }

    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_COURSE = "UPDATE Sighting SET hero_id = ?, location_id = ?, "
                + "dateT = ? WHERE id = ?";
        jdbc.update(UPDATE_COURSE,
                sighting.getHeroID(),
                sighting.getLocationID(),
                sighting.getDate(),
                sighting.getSightingID());

    }

    @Transactional
    public void deleteSightingByID(int id) {
        final String DELETE_COURSE_STUDENT = "DELETE FROM course_student WHERE courseId = ?";
        jdbc.update(DELETE_COURSE_STUDENT, id);

        final String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
        jdbc.update(DELETE_COURSE, id);
    }








    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("sighting_id"));
            sighting.setHeroID(rs.getInt("hero_id"));
            sighting.setLocationID(rs.getInt("location_id"));
            sighting.setDate(rs.getDate("dateT"));


            return sighting;
        }
    }
}
