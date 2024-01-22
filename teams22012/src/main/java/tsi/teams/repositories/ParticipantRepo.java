package tsi.teams.repositories;

import org.springframework.data.repository.CrudRepository;
import tsi.teams.models.Participant;

import java.util.Optional;

public interface ParticipantRepo extends CrudRepository<Participant, Long> {
    Participant findByEmail(String email);
}