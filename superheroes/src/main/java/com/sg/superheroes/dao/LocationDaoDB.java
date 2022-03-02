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
import java.util.List;

@Repository
public class LocationDaoDB {

    @Autowired
    JdbcTemplate jdbc;




    //GET Location FOR A GIVEN ID
    public Location getLocationByID(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE location_id = ?";
            Location loc = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
            return loc;
        } catch(DataAccessException ex) {
            return null;
        }
    }


    //GET ALL Locations
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
        List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
        return locations;
    }


    //ADD LOCATION METHOD
    @Transactional
    public Location addLocation(Location loc) {
        final String INSERT_ORG = "INSERT INTO Location(location_name, location_description, address, location) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORG,
                loc.getLocation_name(),
                loc.getLocation_description(),
                loc.getAddress(),
                loc.getLocation());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setLocation_id(newId);
        return loc;
    }

    //UPDATE A LOCATION
    @Transactional
    public void updateLocation(Location location) {
        final String UPDATE_COURSE = "UPDATE Location SET location_name = ?, location_description = ?, "
                + "address = ?, location=? WHERE location_id = ?";
        jdbc.update(UPDATE_COURSE,
                location.getLocation_name(),
                location.getLocation_description(),
                location.getAddress(),
                location.getLocation(),
                location.getLocation_id());

        final String DELETE_LOCATION_FROM_SIGHTING = "DELETE FROM Sighting WHERE location_id = ?";
        jdbc.update(DELETE_LOCATION_FROM_SIGHTING, location.getLocation_id());
    }



    //DELETE A LOCATION
    @Transactional
    public void deleteLocation(int id) {
        final String DELETE_LOCATION_FROM_SIGHTING = "DELETE FROM Sighting WHERE location_id = ?";
        jdbc.update(DELETE_LOCATION_FROM_SIGHTING, id);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE location_id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }




    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocation_id(rs.getInt("location_id"));
            location.setLocation_name(rs.getString("location_name"));
            location.setLocation_description(rs.getString("location_description"));
            location.setAddress(rs.getString("address"));
            location.setLocation("location");

            return location;
        }
    }

}
