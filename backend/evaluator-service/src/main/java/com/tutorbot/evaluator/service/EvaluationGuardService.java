package com.tutorbot.evaluator.service;

import com.tutorbot.evaluator.exception.SkillInactiveException;
import com.tutorbot.evaluator.exception.StudentNotEnrolledException;
import com.tutorbot.evaluator.exception.TopicInactiveException;
import com.tutorbot.evaluator.exception.TopicNotFoundException;
import com.tutorbot.evaluator.repository.LearningPathRepository;
import com.tutorbot.evaluator.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class EvaluationGuardService {
    private final TopicRepository topicRepository;
    private final LearningPathRepository learningPathRepository;
    
    public EvaluationGuardService(TopicRepository topicRepository, LearningPathRepository learningPathRepository) {
        this.topicRepository = topicRepository;
        this.learningPathRepository = learningPathRepository;
    }
    
    public void validate(String studentId, Long topicId) {
        validateTopicExists(topicId);
        validateTopicIsActive(topicId);
        validateSkillIsActive(topicId);
        validateStudentEnrollment(studentId, topicId);
    }
    
    private void validateTopicExists(Long topicId) throws TopicNotFoundException {
        if (!topicRepository.existsById(topicId)) throw new TopicNotFoundException(topicId);
    }
    
    private void validateTopicIsActive(Long topicId) throws TopicInactiveException {
        topicRepository
                .findActiveById(topicId)
                .orElseThrow(() -> new TopicInactiveException(topicId));
    }
    
    private void validateSkillIsActive(Long topicId) throws SkillInactiveException {
        Boolean isSkillActive = topicRepository.isSkillActiveByTopicId(topicId);
        if (isSkillActive == null || !isSkillActive) throw new SkillInactiveException(topicId);
    }
    
    private void validateStudentEnrollment(String studentId, Long topicId) throws StudentNotEnrolledException {
        if (!learningPathRepository.existsByStudentIdAndTopicId(studentId, topicId)) 
            throw new StudentNotEnrolledException(studentId, topicId);
    }
}
