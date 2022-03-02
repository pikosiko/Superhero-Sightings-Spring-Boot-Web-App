package com.sg.superheroes.controllers;

import com.sg.superheroes.dao.HeroDaoDB;
import com.sg.superheroes.dao.LocationDaoDB;
import com.sg.superheroes.dao.OrganisationDaoDB;
import com.sg.superheroes.dao.SightingDaoDB;
import com.sg.superheroes.entities.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class HeroController {

    @Autowired
    HeroDaoDB heroDaoDB;

    @Autowired
    LocationDaoDB locationDaoDB;

    @Autowired
    OrganisationDaoDB organisationDaoDB;

    @Autowired
    SightingDaoDB sightingDaoDB;


    //USED TO DISPLAY ALL SUPERHEROES
    @GetMapping("superheroes")
    public String displayHeroes(Model model) {
        List<Hero> heroList = heroDaoDB.getAllHeroes();
        model.addAttribute("superheroes", heroList);
        return "superheroes";
    }


    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String firstName = request.getParameter("heroName");
        String lastName = request.getParameter("heroDescription");
        String specialty = request.getParameter("superpower");

        Hero hero = new Hero();
        hero.setHeroName(firstName);
        hero.setHeroDescription(lastName);
        hero.setSuperpower(specialty);

        heroDaoDB.addHero(hero);

        return "redirect:/superheroes";
    }


    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDaoDB.deleteHero(id);

        return "redirect:/superheroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDaoDB.getHeroByID(id);

        model.addAttribute("superhero", hero);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDaoDB.getHeroByID(id);

        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDescription(request.getParameter("heroDescription"));
        hero.setSuperpower(request.getParameter("superpower"));

        heroDaoDB.updateHero(hero);

        return "redirect:/superheroes";
    }

}
