package tsi.teams.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tsi.teams.models.Evenement;
import tsi.teams.models.Invitation;
import tsi.teams.models.Participant;
import tsi.teams.services.EvenementService;
import tsi.teams.services.InvitationService;
import tsi.teams.services.ParticipantService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class EvenementController {

    @Autowired
    private final EvenementService evenementService;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private InvitationService invitationService;

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
    public String getAllEvenements(HttpSession session, Model model) {

        Participant currentUser = (Participant) session.getAttribute("user");

        if (currentUser != null) {
            List<Evenement> events = evenementService.getAllEvents();
            model.addAttribute("events", events);
            model.addAttribute("user", currentUser);
            if(currentUser.getAdmin()){
                model.addAttribute("admin", currentUser);
            }
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
                Participant user = (Participant) session.getAttribute("user");
                String userEmail = user.getEmail();

                List<Invitation> invites = invitationService.getInvitationsByEvent(myEvent);

                // Liste des IDs des participants déjà invités ou organisateur
                List<Long> participantsInvites = invites.stream()
                        .map(invit -> invit.getInvited().getId())
                        .collect(Collectors.toList());
                participantsInvites.add(myEvent.getOrganizer().getId());

                // Liste des participants qui ne sont ni l'organisateur ni déjà invités
                List<Participant> othersUsers = participantService.getAllParticipants().stream()
                        .filter(participant ->
                                participant.getId() != user.getId() &&
                                        !participantsInvites.contains(participant.getId()) &&
                                        !myEvent.getParticipants().contains(participant))
                        .collect(Collectors.toList());

                model.addAttribute("participants", myEvent.getParticipants());
                model.addAttribute("myEvent", myEvent);
                model.addAttribute("user", user);
                model.addAttribute("invites", invites);

                // Si l'utilisateur est l'organisateur
                if(userEmail.equals(myEvent.getOrganizer().getEmail())){
                    model.addAttribute("editEvent", myEvent);
                }

                // Si l'utilisateur est l'organisateur et il y a de la place pour plus d'invitations
                if (userEmail.equals(myEvent.getOrganizer().getEmail()) && myEvent.getParticipants().size() + invites.size() < myEvent.getMaxNbPart()) {
                    model.addAttribute("invitation", new Invitation());
                    model.addAttribute("users", othersUsers);
                }

                // Si l'utilisateur n'est pas l'organisateur et n'est pas déjà inscrit
                boolean dontShowInscription = myEvent.getParticipants().stream()
                        .noneMatch(participant -> participant.getId() == user.getId());
                if (!userEmail.equals(myEvent.getOrganizer().getEmail()) && dontShowInscription && myEvent.getParticipants().size() < myEvent.getMaxNbPart()) {
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

    @GetMapping("/events/{id}/edit")
    public String showEditEvent(@PathVariable Long id, HttpSession session, Model model) {

        if (session.getAttribute("user") != null) {
            Evenement myEvent = evenementService.findById(id);
            if (myEvent != null) {
                Participant user = (Participant) session.getAttribute("user");
                if (user.getId() == myEvent.getOrganizer().getId()) {
                    model.addAttribute("myEvent", myEvent);
                    return "formEdit";
                } else {
                    return "redirect:/error";
                }
            } else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/home/signIn";
        }
    }

    @PostMapping("/events/{id}/edit")
    public String editEvent(@PathVariable Long id, HttpSession session, @ModelAttribute Evenement evenement){

        Participant user = (Participant) session.getAttribute("user");
        if (user != null) {
            Evenement event = evenementService.findById(id);
            if(event != null){
                if(user.getId() == event.getOrganizer().getId()){
                    // Mise à jour des attributs de l'événement existant
                    event.setTitle(evenement.getTitle());
                    event.setDescription(evenement.getDescription());
                    event.setBeginningDate(evenement.getBeginningDate());
                    event.setDuration(evenement.getDuration());
                    event.setMaxNbPart(evenement.getMaxNbPart());

                    event.getParticipants().clear();

                    // Suppression des invités
                    List<Invitation> invitations = invitationService.getInvitationsByEvent(event);
                    for (Invitation invitation : invitations) {
                        invitationService.deleteInvitation(invitation.getId());
                    }
                    // Enregistrement de l'événement mis à jour
                    evenementService.saveEvent(event);
                    return "redirect:/events/" + id;
                } else {
                    return "redirect:/error1";
                }
            } else {
                return "redirect:/error2";
            }
        } else {
            return "redirect:/error3";
        }
    }



    @GetMapping("/events/{id}/inscr")
    public String addPartipant(@PathVariable Long id, HttpSession session) {

        if (session.getAttribute("user") != null) {
            Evenement myEvent = evenementService.findById(id);
            if (myEvent != null) {
                Participant user = (Participant) session.getAttribute("user");
                List<Invitation> invitations = invitationService.getInvitationsByEvent(myEvent);
                for(Invitation invit: invitations){
                    if(invit.getInvited().getId() == user.getId()){
                        invitationService.deleteInvitation(invit.getId());
                    }
                }
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

        Participant currentUser = (Participant) session.getAttribute("user");
        if(currentUser != null){
            Evenement myEvent = evenementService.findById(id);
            if(currentUser.getId() == myEvent.getOrganizer().getId()){
                evenementService.deleteEvent(id);
                return "redirect:/events";
            } else {
                return "redirect:/home";
            }
        } else {
            return "redirect:/home/signIn";
        }
    }

    @GetMapping("/events/{id}/delete")
    public String deleteEvent(@PathVariable Long id, HttpSession session) {

        Participant currentUser = (Participant) session.getAttribute("user");
        if(currentUser != null){
            Evenement myEvent = evenementService.findById(id);
            if(currentUser.getAdmin()){
                evenementService.deleteEvent(id);
                return "redirect:/events";
            } else {
                return "redirect:/home";
            }
        } else {
            return "redirect:/home/signIn";
        }
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
            return "redirect:/home/signIn";
        }
    }

    @PostMapping("/events/{id}/invite")
    public String createInvitation(@PathVariable Long id, @ModelAttribute Invitation invitation, HttpSession session){
        if (session.getAttribute("user") != null) {

            Evenement event = evenementService.findById(id);
            Participant organizer = event.getOrganizer();
            Participant invited = invitation.getInvited();
            invitationService.sendInvitation(organizer, invited, event);
            return "redirect:/events/{id}";
        } else {
            return "redirect:/home/signIn";
        }
    }



}