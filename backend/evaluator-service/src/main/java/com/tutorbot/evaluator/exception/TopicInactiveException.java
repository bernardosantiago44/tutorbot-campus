package com.tutorbot.evaluator.exception;

public class TopicInactiveException extends RuntimeException {
    public TopicInactiveException(Long topicId) {
        super("Inactive topic id: " + topicId);
    }
}
