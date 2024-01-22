package tsi.teams.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "evenement_participant",
            joinColumns = @JoinColumn(name = "evenement_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants;

    public Evenement() {
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public LocalDate getBeginningDate() {
        return beginningDate;
    }
    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getMaxNbPart() {
        return maxNbPart;
    }
    public void setMaxNbPart(int maxNbPart) {
        this.maxNbPart = maxNbPart;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Participant getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Participant organizer) {
        this.organizer = organizer;
    }
    public String getTypeOfEvent() {
        return typeOfEvent;
    }
    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }
    public List<Participant> getParticipants() {
        return participants;
    }
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}