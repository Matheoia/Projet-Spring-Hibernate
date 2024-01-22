package tsi.teams.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tsi.teams.models.Evenement;
import tsi.teams.models.Participant;
import tsi.teams.services.EvenementService;
import tsi.teams.services.ParticipantService;

import java.util.List;


@Controller
public class EvenementController {

    @Autowired
    private final EvenementService evenementService;
    @Autowired
    private ParticipantService participantService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        List<Evenement> events = evenementService.getAllEvents();
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("events", events);
        return "home";
    }

    @GetMapping("/events")
    public String getAllEvenements(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            List<Evenement> events = evenementService.getAllEvents();
            model.addAttribute("events", events);
            model.addAttribute("user", session.getAttribute("user"));
            return "events";
        } else {
            return "redirect:/home/signIn";
        }
    }

    @GetMapping("/events/add")
    public String showFormAddEvent(Model model, HttpSession session) {

        if (session.getAttribute("user") != null) {
            Evenement event = new Evenement();
            model.addAttribute("event", event);
            model.addAttribute("user", session.getAttribute("user"));
            return "addEvent";
        } else {
            return "redirect:/home/signIn";
        }

    }

    @PostMapping("/events/add")
    public String addEvent(HttpSession session, Evenement event) {

        Participant user = (Participant) session.getAttribute("user");
        event.setOrganizer(user);
        evenementService.saveEvent(event);
        return "redirect:/events";

    }

    @GetMapping("/events/{id}")
    public String showEventDetails(@PathVariable Long id, Model model, HttpSession session) {

        if (session.getAttribute("user") != null) {
            Evenement myEvent = evenementService.findById(id);
            if (myEvent != null) {

                List<Participant> participants = myEvent.getParticipants();
                model.addAttribute("participants", participants);
                model.addAttribute("myEvent", myEvent);
                model.addAttribute("user", session.getAttribute("user"));

                Participant user = (Participant) session.getAttribute("user");
                String userEmail = user.getEmail();

                boolean dontShowInscritpion = Boolean.TRUE;

                for (Participant participant : participants) {
                    if (participant.getId() == user.getId()) {
                        dontShowInscritpion = Boolean.FALSE;
                        break;
                    }
                }

                if (!userEmail.equals(myEvent.getOrganizer().getEmail()) && dontShowInscritpion && participants.size() < myEvent.getMaxNbPart() - 1) {
                    model.addAttribute("event", myEvent);
                }

                return "myEvent";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/home/signIn";
        }
    }

    @GetMapping("/events/{id}/inscr")
    public String addPartipant(@PathVariable Long id, HttpSession session) {

        if (session.getAttribute("user") != null) {
            Evenement myEvent = evenementService.findById(id);
            if (myEvent != null) {
                Participant user = (Participant) session.getAttribute("user");
                myEvent.getParticipants().add(user);
                evenementService.saveEvent(myEvent);
                return "redirect:/events/{id}";
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/home/signIn";
        }
    }

    @GetMapping("/events/{id}/cancel")
    public String cancelEvent(@PathVariable Long id, HttpSession session) {
        Evenement myEvent = evenementService.findById(id);
        evenementService.deleteEvent(id);
        return "redirect:/events";
    }

    @GetMapping("/events/{id}/cancelInscr")
    public String cancelInscr(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("user") != null) {
            Participant user = (Participant) session.getAttribute("user");
            Evenement myEvent = evenementService.findById(id);
            List<Participant> participants = myEvent.getParticipants();

            int indexToRemove = -1;
            for (int i = 0; i < participants.size(); i++) {
                long participantId = participants.get(i).getId();
                if(participantId == user.getId()){
                    indexToRemove = i;
                    break;
                }
            }
            if (indexToRemove != -1) {
                participants.remove(indexToRemove);
                evenementService.saveEvent(myEvent);
            }

            return "redirect:/events/{id}";
        } else {
            return "redirect:/home";
        }

    }



}