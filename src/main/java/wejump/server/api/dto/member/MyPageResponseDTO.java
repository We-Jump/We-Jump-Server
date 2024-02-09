package wejump.server.api.dto.member;

import lombok.Builder;
import lombok.Getter;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import java.util.List;

@Getter
@Builder
public class MyPageResponseDTO {

    private List<CourseResponseDTO> courses;

    private List<CourseResponseDTO> myCourses;
}
