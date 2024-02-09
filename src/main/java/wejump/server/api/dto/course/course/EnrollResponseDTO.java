package wejump.server.api.dto.course.course;

import lombok.Builder;
import lombok.Getter;
import wejump.server.domain.course.EnrollCourseId;

import java.time.LocalDate;

@Getter
@Builder
public class EnrollResponseDTO {
    private EnrollCourseId id;
    private String name;
    private LocalDate enrolledDate;
    private Boolean accepted;
}
