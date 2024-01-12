package tsi.teams.repositories;

import org.springframework.data.repository.CrudRepository;
import tsi.teams.models.Evenement;
public interface EvenementRepo extends CrudRepository<Evenement, Long> {
}