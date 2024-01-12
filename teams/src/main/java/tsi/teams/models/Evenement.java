package tsi.teams.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String theme;
    private LocalDate beginningDate;
    private int duration;
    private int maxNbPart;
    private String description;
    @ManyToOne
    private Participant organizer;
    private String typeOfEvent;

    public Evenement() {
        this.title = "Title";
        this.theme = "TD";
        this.beginningDate = LocalDate.of(2024, 1, 1);
        this.duration = 1;
        this.maxNbPart = 10;
        this.description = "Description";
        this.organizer = new Participant();
        this.typeOfEvent = "Basketball Game";
    }

    public Evenement(String title, String theme, LocalDate beginningDate, int duration, int maxNbPart, String description, Participant organizer, String typeOfEvent){
        this.title = title;
        this.theme = theme;
        this.beginningDate = beginningDate;
        this.duration = duration;
        this.maxNbPart = maxNbPart;
        this.description = description;
        this.organizer = organizer;
        this.typeOfEvent = typeOfEvent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}