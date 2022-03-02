package com.sg.superheroes.controllers;

import com.sg.superheroes.dao.HeroDaoDB;
import com.sg.superheroes.dao.LocationDaoDB;
import com.sg.superheroes.dao.OrganisationDaoDB;
import com.sg.superheroes.dao.SightingDaoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SightingController {

    @Autowired
    HeroDaoDB heroDaoDB;

    @Autowired
    LocationDaoDB locationDaoDB;

    @Autowired
    OrganisationDaoDB organisationDaoDB;

    @Autowired
    SightingDaoDB sightingDaoDB;
}
