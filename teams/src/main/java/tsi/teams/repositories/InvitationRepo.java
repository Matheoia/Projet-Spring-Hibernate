package tsi.teams.repositories;

import org.springframework.data.repository.CrudRepository;
import tsi.teams.models.Evenement;
import tsi.teams.models.Invitation;
import tsi.teams.models.Participant;

import java.util.List;

public interface InvitationRepo extends CrudRepository<Invitation, Long>  {
    List<Invitation> findByEvent(Evenement event);
    List<Invitation> findByInvited(Participant participant);

}
