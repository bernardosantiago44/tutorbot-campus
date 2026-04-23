package com.tutorbot.evaluator.exception;

public class SkillInactiveException extends RuntimeException {
    public SkillInactiveException(Long topicId) {
        super("The parent skill for the topic " + topicId + " is not active.");
    }
}
