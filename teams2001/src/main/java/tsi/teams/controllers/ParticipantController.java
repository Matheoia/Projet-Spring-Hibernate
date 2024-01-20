package tsi.teams.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tsi.teams.models.Evenement;
import tsi.teams.models.Participant;
import tsi.teams.services.EvenementService;
import tsi.teams.services.ParticipantService;
import tsi.teams.config.SecurityConfig;

import java.awt.event.PaintEvent;
import java.util.List;

@Controller
public class ParticipantController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private EvenementService evenementService;

    @GetMapping("/")
    public String goHome(){
        return "redirect:/home";
    }

    @GetMapping("/home/signUp")
    public String showSignUpForm(Model model){
        model.addAttribute("participant", new Participant());
        return "signUp";
    }

    @PostMapping("/home/signUp")
    public String signUp(@ModelAttribute("participant") Participant participant, HttpSession session, Model model){

        String email = participant.getEmail();
        if (participantService.findByEmail(email) != null) {
            model.addAttribute("error", "Un utilisateur avec cette adresse e-mail existe déjà.");
            return "signUp";
        }

        String hashedPassword = passwordEncoder.encode(participant.getPassword());
        participant.setPassword(hashedPassword);
        participantService.save(participant);

        session.setAttribute("user", participant);

        return "redirect:/events";
    }

    @GetMapping("/home/signIn")
    public String showSignInForm(){

        return "signIn";
    }

    @PostMapping("/home/signIn")
    public String signIn(@RequestParam String email, @RequestParam String password, HttpSession session, Model model){

        Participant user = participantService.findByEmail(email);

        if (user == null){
            model.addAttribute("error", "Aucun compte avec cette adresse e-mail.");
            return "signIn";

        } else if (passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("user", user);
            return "redirect:/events";

        } else {
            model.addAttribute("error", "Mauvais identifiants");
            return "signIn";
        }
    }

    @GetMapping("/logout")
    String logout(HttpSession session, Model model) {
        session.setAttribute("user", null);
        return "redirect:/home";
    }

    @GetMapping("/users")
    public String showUsers(HttpSession session, Model model) {


        if (session.getAttribute("user") != null) {
            List<Participant> participants = participantService.getAllParticipants();
            model.addAttribute("participants", participants);
            model.addAttribute("user", session.getAttribute("user"));
            return "users";
        } else {
            return "redirect:/home/signIn";
        }
    }

    @GetMapping("/users/{id}")
    public String showEventDetails(@PathVariable Long id, Model model, HttpSession session) {

        Participant currentUser = (Participant) session.getAttribute("user");
        if(currentUser != null){
            Participant visitedUser = participantService.getParticipantById(id);
            model.addAttribute("user", currentUser);

            List<Evenement> createdEvents = evenementService.getEventsCreatedByParticipant(visitedUser);
            List<Evenement> registeredEvents = evenementService.getEventsParticipantIsRegistered(visitedUser);
            model.addAttribute("createdEvents", createdEvents);
            model.addAttribute("registeredEvents", registeredEvents);

            if(visitedUser.getId() !=  currentUser.getId()) {
                model.addAttribute("visitedUser", visitedUser);
            }
            return "oneProfile";

        } else {
            return "redirect:/home/signIn";
        }
    }


}

