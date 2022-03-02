package com.sg.superheroes.entities;

import java.util.List;
import java.util.Objects;

public class Location {


    private int location_id;
    private String location_name;
    private String location_description;
    private String address;
    private String  location;
    private List<Sighting> sightings;

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return location_id == location1.location_id && Objects.equals(location_name, location1.location_name) && Objects.equals(location_description, location1.location_description) && Objects.equals(address, location1.address) && Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location_id, location_name, location_description, address, location);
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_description() {
        return location_description;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
