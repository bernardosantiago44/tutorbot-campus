package com.tutorbot.evaluator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SKILLS")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_skills")
    @SequenceGenerator(name = "seq_skills", sequenceName = "SEQ_SKILLS", allocationSize = 1)
    private Long id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ACTIVE")
    private Boolean active;
    
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
