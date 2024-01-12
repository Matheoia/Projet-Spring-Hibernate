package tsi.teams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tsi.teams.models.Participant;
import tsi.teams.services.ParticipantService;

import java.awt.event.PaintEvent;
import java.util.List;

@Controller
public class ParticipantController {

//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/signUp")
    public String showSignUpForm(Model model){
        model.addAttribute("participant", new Participant());
        return "signUp";
    }

    @GetMapping("/signIn")
    public String showSignInForm(){
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam String email, @RequestParam String password){
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<Participant> participants = participantService.getAllParticipants();
        model.addAttribute("participants", participants);
        return "users";
    }

//    @PostMapping("/login")
//    public String createUser(@ModelAttribute Participant participant){
////        String encryptedPassword = passwordEncoder.encode(participant.getPassword());
////        participant.setPassword(encryptedPassword);
//        participantService.save(participant);
//        return "redirect:/users";
//    }

}

