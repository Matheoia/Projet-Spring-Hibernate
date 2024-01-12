package tsi.teams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.teams.models.Evenement;
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
    public Evenement getEventById(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }
    public Evenement saveEvent(Evenement evenement) {
        return evenementRepository.save(evenement);
    }
    public void deleteEvent(Long id) {
        evenementRepository.deleteById(id);
    }
}