package com.sg.superheroes.entities;

import java.util.List;
import java.util.Objects;

public class Organisation {

    private int org_id;
    private String org_name;
    private String org_description;
    private String address;
    private List<Hero> members;

    public List<Hero> getMembers() {
        return members;
    }

    public void setMembers(List<Hero> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return org_id == that.org_id && Objects.equals(org_name, that.org_name) && Objects.equals(org_description, that.org_description) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(org_id, org_name, org_description, address);
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_description() {
        return org_description;
    }

    public void setOrg_description(String org_description) {
        this.org_description = org_description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
