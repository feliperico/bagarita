package br.frico.bagarita.domain.question;

import javax.persistence.*;

/**
 * Represents a source of questions.
 *
 * Created by Felipe Rico on 8/17/2016.
 */
@Entity
@Table(name = "BAG_QUESTION_SOURCE")
public class QuestionSource {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", sequenceName = "BAG_SEQUENCE")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    private String description;

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
