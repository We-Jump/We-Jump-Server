package wejump.server.api.dto.course.people;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import wejump.server.domain.assignment.EvaluateId;
import wejump.server.domain.lesson.AttendId;

import java.util.List;

@Getter
@Builder
public class StatusDTO {
    private String name;
    private String attendance;
    private String evaluation;
    private Long memberId;
    private Long lessonId;
    private Long assignmentId;
}
