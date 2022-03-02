package com.sg.superheroes.entities;

import java.util.List;
import java.util.Objects;

public class Hero {

    private int hero_id;
    private String heroName;
    private String heroDescription;
    private String superpower;
    private List<Organisation> organisations;

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return hero_id == hero.hero_id && Objects.equals(heroName, hero.heroName) && Objects.equals(heroDescription, hero.heroDescription) && Objects.equals(superpower, hero.superpower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hero_id, heroName, heroDescription, superpower);
    }

    public int getHero_id() {
        return hero_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }
}
