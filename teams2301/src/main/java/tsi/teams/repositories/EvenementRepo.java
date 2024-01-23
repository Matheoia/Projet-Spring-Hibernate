package tsi.teams.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import tsi.teams.models.Evenement;
import tsi.teams.models.Participant;

import java.util.List;

public interface EvenementRepo extends CrudRepository<Evenement, Long> {
    List<Evenement> findByParticipantsContaining(Participant participant);

    List<Evenement> findByOrganizer(Participant participant);

    Object findAll(Sort beginningDate);
}