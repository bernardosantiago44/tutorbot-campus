package com.tutorbot.evaluator.service;

import com.tutorbot.evaluator.exception.SkillInactiveException;
import com.tutorbot.evaluator.exception.StudentNotEnrolledException;
import com.tutorbot.evaluator.exception.TopicInactiveException;
import com.tutorbot.evaluator.exception.TopicNotFoundException;
import com.tutorbot.evaluator.model.Skill;
import com.tutorbot.evaluator.model.Topic;
import com.tutorbot.evaluator.repository.LearningPathRepository;
import com.tutorbot.evaluator.repository.TopicRepository;
import jdk.jfr.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class EvaluationGuardServiceTests {
    private TopicRepository topicRepository;
    private LearningPathRepository learningPathRepository;
    private EvaluationGuardService guard;
    
    @BeforeEach
    void setup() {
        topicRepository = Mockito.mock(TopicRepository.class);
        learningPathRepository = Mockito.mock(LearningPathRepository.class);
        guard = new EvaluationGuardService(topicRepository, learningPathRepository);
    }
    
    @Test()
    @Name("Happy Path: Valid student and topic should pass")
    void happyPath() {
        Long topicId = 1L;
        String studentId = "A00835009";

        Skill skill = new Skill();
        skill.setId(2L);
        skill.setActive(true);

        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setActive(true);
        topic.setSkill(skill);

        when(topicRepository.existsById(topicId)).thenReturn(true);
        when(topicRepository.findActiveById(topicId)).thenReturn(Optional.of(topic));
        when(topicRepository.isSkillActiveByTopicId(topicId)).thenReturn(true);
        when(learningPathRepository.existsByStudentIdAndTopicId(studentId, topicId)).thenReturn(true);

        assertDoesNotThrow(() -> guard.validate(studentId, topicId));
    }
    
    @Test
    @Name("Topic not found")
    void topicDoesNotExistException() {
        Long topicId = Long.MAX_VALUE;
        String studentId = "A01638915";
        
        when(topicRepository.existsById(topicId)).thenReturn(false);
        
        assertThrows(TopicNotFoundException.class, () -> guard.validate(studentId, topicId));
    }
    
    @Test
    @Name("Topic exists and is inactive")
    void topicIsInactive() {
        Long topicId = 2L;
        String studentId = "A01638915";
        
        when(topicRepository.existsById(topicId)).thenReturn(true);
        when(topicRepository.findActiveById(topicId)).thenReturn(Optional.empty());
        
        assertThrows(TopicInactiveException.class, () -> guard.validate(studentId, topicId));
    }
    
    @Test
    @Name("Skill inactive")
    void skillInactiveException() {
        Long topicId = 2L;
        String studentId = "A01638915";

        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setActive(true);

        when(topicRepository.existsById(topicId)).thenReturn(true);
        when(topicRepository.findActiveById(topicId)).thenReturn(Optional.of(topic));
        when(topicRepository.isSkillActiveByTopicId(topicId)).thenReturn(false);

        assertThrows(SkillInactiveException.class, () -> guard.validate(studentId, topicId));
    }
    
    @Test
    @Name("Student not enrolled exception")
    void studentNotEnrolled() {
        Long topicId = 1L;
        String studentId = "A01638915";

        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setActive(true);

        when(topicRepository.existsById(topicId)).thenReturn(true);
        when(topicRepository.findActiveById(topicId)).thenReturn(Optional.of(topic));
        when(topicRepository.isSkillActiveByTopicId(topicId)).thenReturn(true);
        when(learningPathRepository.existsByStudentIdAndTopicId(studentId, topicId)).thenReturn(false);

        assertThrows(StudentNotEnrolledException.class, () -> guard.validate(studentId, topicId));
    }
}
