package tsi.teams.models;

import jakarta.persistence.*;

@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Participant organizer;

    @ManyToOne
    @JoinColumn(name = "invited_id")
    private Participant invited;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Evenement event;

    public Invitation(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Participant organizer) {
        this.organizer = organizer;
    }

    public Participant getInvited() {
        return invited;
    }

    public void setInvited(Participant invited) {
        this.invited = invited;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

}
