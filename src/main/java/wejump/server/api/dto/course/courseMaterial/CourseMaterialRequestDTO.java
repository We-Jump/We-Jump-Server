package wejump.server.api.dto.course.courseMaterial;

import lombok.Getter;
import wejump.server.api.dto.course.assignment.AssignmentRequestDTO;
import wejump.server.api.dto.course.lesson.LessonRequestDTO;

@Getter
public class CourseMaterialRequestDTO {

    private LessonRequestDTO lessonRequestDTO;

    private AssignmentRequestDTO assignmentRequestDTO;
}
