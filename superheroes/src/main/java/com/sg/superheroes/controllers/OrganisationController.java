package com.sg.superheroes.controllers;


import com.sg.superheroes.dao.HeroDaoDB;
import com.sg.superheroes.dao.LocationDaoDB;
import com.sg.superheroes.dao.OrganisationDaoDB;
import com.sg.superheroes.dao.SightingDaoDB;
import com.sg.superheroes.entities.Hero;
import com.sg.superheroes.entities.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganisationController {

    @Autowired
    HeroDaoDB heroDaoDB;

    @Autowired
    LocationDaoDB locationDaoDB;

    @Autowired
    OrganisationDaoDB organisationDaoDB;

    @Autowired
    SightingDaoDB sightingDaoDB;



    //USED TO DISPLAY ALL ORGANIZATIONS
    @GetMapping("organisations")
    public String displayOrganisations(Model model) {

        List<Organisation> organisations = organisationDaoDB.getAllOrgs();
        List<Hero> heroes = heroDaoDB.getAllHeroes();
        model.addAttribute("organisations", organisations);
        model.addAttribute("superheroes", heroes);
        return "organisations";
    }

    //ADD AN ORGANIZATION
    @PostMapping("addOrganisation")
    public String addOrganisation(Organisation organisation, HttpServletRequest request) {

        String[] heroIds = request.getParameterValues("superheroID");

        List<Hero> heroes = new ArrayList<>();

        for(String heroId: heroIds){
            heroes.add(heroDaoDB.getHeroByID(Integer.parseInt(heroId)));
        }

        organisation.setMembers(heroes);

        organisationDaoDB.addOrg(organisation);

        return "redirect:/organisations";
    }

    //GET ALL THE HEROES FOR ORGANIZATION
    @GetMapping("orgDetails")
    public String orgDetails(Integer id, Model model) {
        Organisation organisation = organisationDaoDB.getOrganisationByID(id);
        model.addAttribute("organisation", organisation);
        return "orgDetails";
    }

    //DELETE ORGANIZATION
    @GetMapping("deleteOrg")
    public String deleteOrg(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organisationDaoDB.deleteOrg(id);

        return "redirect:/organisations";
    }

    //EDIT ORGANIZATION
    @GetMapping("editOrg")
    public String editOrg(Integer id, Model model) {

        Organisation organisation = organisationDaoDB.getOrganisationByID(id);
        List<Hero> heroes = heroDaoDB.getAllHeroes();

        model.addAttribute("organisation", organisation);
        model.addAttribute("superheroes", heroes);
        return "editOrg";
    }


    //PERFORM EDIT ORGANIZATION
    @PostMapping("editOrg")
    public String performEditOrg(Organisation organisation, HttpServletRequest request) {

        String[] heroIds = request.getParameterValues("superheroID");

        List<Hero> heroes = new ArrayList<>();

        for(String heroId: heroIds){
            heroes.add(heroDaoDB.getHeroByID(Integer.parseInt(heroId)));
        }

        organisation.setMembers(heroes);

        organisationDaoDB.updateOrg(organisation);

        return "redirect:/organisations";
    }
}
