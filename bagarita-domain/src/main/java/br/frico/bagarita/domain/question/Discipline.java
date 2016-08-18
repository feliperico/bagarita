package br.frico.bagarita.domain.question;

import javax.persistence.*;

/**
 * Discipline Entity
 *
 * Created by felipe on 8/15/16.
 */
@Entity
@Table(name = "BAG_DISCIPLINE")
public class Discipline {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", sequenceName = "bag_sequence")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    public Discipline() {

    }
    public Discipline(String name, String description) {
        this.name = name;
        this.description = description;
    }

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
}
