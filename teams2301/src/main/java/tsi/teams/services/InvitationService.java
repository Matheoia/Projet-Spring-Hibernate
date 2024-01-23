package tsi.teams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.teams.models.Evenement;
import tsi.teams.models.Invitation;
import tsi.teams.models.Participant;
import tsi.teams.repositories.EvenementRepo;
import tsi.teams.repositories.InvitationRepo;

import java.util.List;

@Service
public class InvitationService {

    @Autowired
    private final InvitationRepo invitationRepository;

    public InvitationService(InvitationRepo invitationRepo) {
        this.invitationRepository = invitationRepo;
    }

    public List<Invitation> getAllInvitations() {
        return (List<Invitation>) invitationRepository.findAll();
    }

    public Invitation getInvitationById(Long id) {
        return invitationRepository.findById(id).orElse(null);
    }

    public List<Invitation> getInvitationsByEvent(Evenement event) {
        return invitationRepository.findByEvent(event);
    }

    public List<Invitation> getReceivedInvitations(Participant participant) {
        return invitationRepository.findByInvited(participant);
    }

    public void sendInvitation(Participant organizer, Participant invited, Evenement event) {
        Invitation invitation = new Invitation();
        invitation.setOrganizer(organizer);
        invitation.setInvited(invited);
        invitation.setEvent(event);

        invitationRepository.save(invitation);
    }

    public void deleteInvitation(Long id) {
        invitationRepository.deleteById(id);
    }
}
