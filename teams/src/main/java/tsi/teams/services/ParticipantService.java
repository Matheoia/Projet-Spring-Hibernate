package tsi.teams.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.teams.models.Participant;
import tsi.teams.repositories.ParticipantRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private final ParticipantRepo participantRepository;

    public ParticipantService(ParticipantRepo participantRepository) {
        this.participantRepository = participantRepository;
    }
    public List<Participant> getAllParticipants() {
        return (List<Participant>) participantRepository.findAll();
    }
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElse(null);
    }
    public Participant save(Participant participant) {
        return participantRepository.save(participant);
    }
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    public Participant findByEmail(String email) {
        return participantRepository.findByEmail(email);
    }


}
