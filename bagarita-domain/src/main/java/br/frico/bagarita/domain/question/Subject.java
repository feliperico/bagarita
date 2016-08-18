package br.frico.bagarita.domain.question;

import javax.persistence.*;

/**
 * Represents a discipline subject
 *
 * Created by Felipe Rico on 8/17/2016.
 */
@Entity
@Table(name = "BAG_SUBJECT")
public class Subject {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", sequenceName = "BAG_SEQUENCE")
    private Long id;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "DISCIPLINE_ID", nullable = false)
    private Discipline discipline;

    public Subject() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
}
