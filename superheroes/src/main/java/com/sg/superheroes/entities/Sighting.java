package com.sg.superheroes.entities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

public class Sighting {


    //REPRESENTS SIGHTINGS TABLE AND ITS DATA

    private int sightingID;
    private int heroID;
    private int locationID;
    private Date date;

    private Hero hero;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return sightingID == sighting.sightingID && heroID == sighting.heroID && locationID == sighting.locationID && Objects.equals(date, sighting.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightingID, heroID, locationID, date);
    }

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public Date getDate() {return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date newDate = Date.valueOf(formatter.format(date));
        this.date = newDate;
    }
}
