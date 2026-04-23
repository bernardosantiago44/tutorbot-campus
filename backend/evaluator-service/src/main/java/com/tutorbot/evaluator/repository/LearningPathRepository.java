package com.tutorbot.evaluator.repository;

import com.tutorbot.evaluator.model.LearningPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LearningPathRepository extends JpaRepository<LearningPathEntity, Long> {
    @Query("select count(path) > 0" +
            "from LearningPathEntity path" +
            "where path.studentId = :studentId" +
            "  and path.topicId = :topicId")
    boolean existsByStudentIdAndTopicId(@Param("studentId") String studentId, @Param("topicId") Long topicId);
}
