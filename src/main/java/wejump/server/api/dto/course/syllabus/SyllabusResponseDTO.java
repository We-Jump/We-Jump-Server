package wejump.server.api.dto.course.syllabus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SyllabusResponseDTO {

    private Long id;

    private Integer week;

    private String title;

    private Boolean video;

    private Boolean assignment;
}
