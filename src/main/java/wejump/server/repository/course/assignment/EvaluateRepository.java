package wejump.server.repository.course.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.assignment.Evaluate;
import wejump.server.domain.assignment.EvaluateId;
import wejump.server.domain.lesson.Attend;

import java.util.List;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, EvaluateId> {
    List<Evaluate> findAllByAssignmentIdOrderByName(Long assignmentId);
    List<Evaluate> findAllByAssignmentIdAndMemberId(Long assignmentId, Long memberId);
}
