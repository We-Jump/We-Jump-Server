package wejump.server.service.course.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.assignment.AssignmentRequestDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.course.assignment.AssignmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Assignment getAssignmentById(Long assignmentId){
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find assignment"));

        return assignment;
    }

    @Transactional
    public Assignment createAssignment(Lesson lesson, AssignmentRequestDTO assignmentDTO) {

        Assignment assignment = Assignment.builder()
                .title(assignmentDTO.getTitle())
                .description(assignmentDTO.getDescription())
                .end(assignmentDTO.getEnd())
                .lesson(lesson)
                .build();

        return assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment updateAssignment(Assignment assignment, AssignmentRequestDTO assignmentRequestDTO) {

        assignment.updateAssignment(assignmentRequestDTO.getTitle(),
                assignmentRequestDTO.getDescription(),
                assignmentRequestDTO.getEnd());

        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void deleteAssignment(Assignment assignment){

        assignmentRepository.delete(assignment);
    }


}
