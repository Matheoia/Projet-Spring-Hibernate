package tsi.teams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tsi.teams.models.Evenement;
import tsi.teams.services.EvenementService;

import java.util.List;


@Controller
public class EvenementController {

    @Autowired
    private final EvenementService evenementService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping("/events")
    public String getAllEvenements(Model model) {
        List<Evenement> events = evenementService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

//    @GetMapping("/{id}")
//    public Evenement getEvenementById(@PathVariable Long id) {
//        return evenementService.getEventById(id);
//    }
//
//    @PostMapping
//    public Evenement saveEvenement(@RequestBody Evenement evenement) {
//        return evenementService.saveEvent(evenement);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteEvenement(@PathVariable Long id) {
//        evenementService.deleteEvent(id);
//    }
}