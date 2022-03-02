package com.sg.superheroes.controllers;

import com.sg.superheroes.dao.HeroDaoDB;
import com.sg.superheroes.dao.LocationDaoDB;
import com.sg.superheroes.dao.OrganisationDaoDB;
import com.sg.superheroes.dao.SightingDaoDB;
import com.sg.superheroes.entities.Hero;
import com.sg.superheroes.entities.Location;
import com.sg.superheroes.entities.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController {

    @Autowired
    HeroDaoDB heroDaoDB;

    @Autowired
    LocationDaoDB locationDaoDB;

    @Autowired
    OrganisationDaoDB organisationDaoDB;

    @Autowired
    SightingDaoDB sightingDaoDB;


    //USED TO DISPLAY ALL LOCATIONS
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDaoDB.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }


    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String locationName = request.getParameter("location_name");
        String locationDescription = request.getParameter("location_description");
        String address = request.getParameter("address");
        String location = request.getParameter("location");

        Location location1 = new Location();
        location1.setLocation_name(locationName);
        location1.setLocation_description(locationDescription);
        location1.setAddress(address);
        location1.setLocation(location);

        locationDaoDB.addLocation(location1);

        return "redirect:/locations";
    }


    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDaoDB.deleteLocation(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDaoDB.getLocationByID(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDaoDB.getLocationByID(id);

        location.setLocation_name(request.getParameter("location_name"));
        location.setLocation_description(request.getParameter("location_description"));
        location.setAddress(request.getParameter("address"));
        location.setLocation(request.getParameter("location"));

        locationDaoDB.updateLocation(location);


        return "redirect:/locations";
    }


}
