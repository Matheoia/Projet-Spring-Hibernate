package tsi.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tsi.teams.models.Evenement;
import tsi.teams.repositories.EvenementRepo;

import java.util.Date;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private EvenementRepo evenementRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        // Création d'un nouvel événement
//        Evenement evenement = new Evenement();
//        evenement.setNom("Événement Test");
//
//        // Enregistrement de l'événement dans la base de données
//        evenementRepository.save(evenement);
//
//    }
}
