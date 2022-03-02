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
public class OrganisationDaoDB {

    @Autowired
    JdbcTemplate jdbc;


    //GET ORG INFO FOR A GIVEN ID
    public Organisation getOrganisationByID(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM Organisation WHERE org_id = ?";
            Organisation org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganisationMapper(), id);
            org.setMembers(getHeroesForOrg(id));
            return org;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    //GET ALL ORGANISATIONS
    public List<Organisation> getAllOrgs() {
        final String SELECT_ALL_ORGS = "SELECT * FROM Organisation";
        List<Organisation> organisations = jdbc.query(SELECT_ALL_ORGS, new OrganisationMapper());
        associateOrgsAndHeroes(organisations);
        return organisations;
    }

    //HELPER METHOD TO ASSOCIATE ORGANISATIONS TO HEROES
    private void associateOrgsAndHeroes(List<Organisation> organisations) {
        for (Organisation org : organisations) {
            org.setMembers(getHeroesForOrg(org.getOrg_id()));
        }
    }


    //GET ALL HEROES FOR AN ORG  !!!TASK3!!!
    public List<Hero> getHeroesForOrg(int id) {
        //SELECT ALL THE HEROES FOR THAT ORGANISATION
        final String SELECT_MEMBERS_FOR_ORG = "SELECT h.* FROM Hero h "
                + "JOIN HeroAtOrg ho ON ho.hero_id = h.hero_id WHERE ho.org_id = ?";
        return jdbc.query(SELECT_MEMBERS_FOR_ORG, new HeroDaoDB.HeroMapper(), id);
    }


    //ADD ORG METHOD
    @Transactional
    public Organisation addOrg(Organisation org) {
        final String INSERT_ORG = "INSERT INTO Organisation(org_name, org_description, address) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getOrg_name(),
                org.getOrg_description(),
                org.getAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrg_id(newId);
        insertOrgHero(org);
        return org;
    }

    //PRIVATE HELPER ADD METHOD THAT CREATES A RELATIONSHIP WITH HERO AND ORG
    private void insertOrgHero(Organisation org) {
        final String INSERT_ORG_HERO = "INSERT INTO "
                + "HeroAtOrg(hero_id, org_id) VALUES(?,?)";
        for(Hero hero : org.getMembers()) {
            jdbc.update(INSERT_ORG_HERO,
                    hero.getHero_id(),
                    org.getOrg_id());
        }
    }


    //UPDATE THE ORG
    @Transactional
    public void updateOrg(Organisation org) {
        final String UPDATE_COURSE = "UPDATE Organisation SET org_name = ?, org_description = ?, "
                + "address = ? WHERE org_id = ?";
        jdbc.update(UPDATE_COURSE,
                org.getOrg_name(),
                org.getOrg_description(),
                org.getAddress(),
                org.getOrg_id());

        final String DELETE_HEROES_FROM_ORG = "DELETE FROM HeroAtOrg WHERE org_id = ?";
        jdbc.update(DELETE_HEROES_FROM_ORG, org.getOrg_id());
        insertOrgHero(org);

    }



    //DELETE AN ORG
    @Transactional
    public void deleteOrg(int id) {
        final String DELETE_HERO_FROM_ORG = "DELETE FROM HeroAtOrg WHERE org_id = ?";
        jdbc.update(DELETE_HERO_FROM_ORG, id);

        final String DELETE_COURSE = "DELETE FROM Organisation WHERE org_id = ?";
        jdbc.update(DELETE_COURSE, id);
    }


    public static final class OrganisationMapper implements RowMapper<Organisation> {

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation organisation = new Organisation();
            organisation.setOrg_id(rs.getInt("org_id"));
            organisation.setOrg_name(rs.getString("org_name"));
            organisation.setOrg_description(rs.getString("org_description"));
            organisation.setAddress(rs.getString("address"));


            return organisation;
        }
    }
}
