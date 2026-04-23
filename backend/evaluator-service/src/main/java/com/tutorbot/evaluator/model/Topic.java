package com.tutorbot.evaluator.model;

import jakarta.persistence.*;
import com.tutorbot.evaluator.model.Skill;

@Entity
@Table(name = "TOPICS")
public class Topic {
    @Id
    private Long id;
    private String name;
    private Long courseId;
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "SKILL_ID")
    private Skill skill;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
