package tsi.teams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.teams.models.Evenement;
import tsi.teams.models.Participant;
import tsi.teams.repositories.EvenementRepo;

import java.util.List;

@Service
public class EvenementService {

    @Autowired
    private final EvenementRepo evenementRepository;

    public EvenementService(EvenementRepo evenementRepository) {
        this.evenementRepository = evenementRepository;
    }
    public List<Evenement> getAllEvents() {
        return (List<Evenement>) evenementRepository.findAll();
    }
    public Evenement findById(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }
    public Evenement saveEvent(Evenement evenement) {
        return evenementRepository.save(evenement);
    }
    public void deleteEvent(Long id) {
        evenementRepository.deleteById(id);
    }

    public List<Evenement> getEventsCreatedByParticipant(Participant participant) {
        return evenementRepository.findByOrganizer(participant);
    }

    public List<Evenement> getEventsParticipantIsRegistered(Participant participant) {
        return evenementRepository.findByParticipantsContaining(participant);
    }

}