package com.tutorbot.evaluator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LEARNING_PATHS")
public class LearningPathEntity {
    @Id
    private Long id;
    @Column(name = "topic_id")
    private Long topicId;
    
    @Column(name = "student_id")
    private String studentId;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
