package wejump.server.repository.course.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.assignment.Submit;
import wejump.server.domain.assignment.SubmitId;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmitRepository extends JpaRepository<Submit, SubmitId> {
    Optional<Submit> findByAssignmentIdAndMemberId(Long assignmentId, Long memberId);

    void deleteByAssignmentIdAndMemberId(Long assignmentId, Long memberId);
}
