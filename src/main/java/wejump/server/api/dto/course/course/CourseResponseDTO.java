package wejump.server.api.dto.course.course;

import lombok.Builder;
import lombok.Getter;
import wejump.server.domain.course.Course;
import wejump.server.domain.lesson.Lesson;
import wejump.server.domain.member.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CourseResponseDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String summary;
    private String reference;
    private String instructor;
    private String image;

}
