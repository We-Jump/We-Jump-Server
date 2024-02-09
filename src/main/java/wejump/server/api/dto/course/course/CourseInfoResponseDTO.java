package wejump.server.api.dto.course.course;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseInfoResponseDTO {
    private String name;
    private String instructorName;
    private String summary;
    private String description;
    private String reference;
}
