package com.tutorbot.evaluator.repository;

import com.tutorbot.evaluator.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("select topic " +
            "from Topics " +
            "where topic.id = :topicId" +
            "  and topic.active = true")
    Optional<Topic> findActiveById(@Param("topicId") Long topicId);

    @Query("SELECT CASE " +
            "WHEN t.skill.active = true " +
            "  THEN true " +
            "  ELSE false " +
            "END " +
            "FROM Topic t " +
            "WHERE t.id = :topicId" +
            "  AND t.skill.active = true")
    Boolean isSkillActiveByTopicId(@Param("topicId") Long topicId);
}
