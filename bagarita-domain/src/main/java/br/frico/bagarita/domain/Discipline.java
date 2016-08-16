package br.frico.bagarita.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Discipline Entity
 *
 * Created by felipe on 8/15/16.
 */
@Entity
@Table(name = "BAG_DISCIPLINE")
public class Discipline {

    @Id
    private Long id;

    private String name;

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
}
