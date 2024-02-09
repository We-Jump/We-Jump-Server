package wejump.server.api.dto.course.course;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CourseRequestDTO {

    @NotBlank(message = "코스 이름은 필수입니다.")
    private String name;

    @NotNull(message = "시작 날짜는 필수입니다.")
    private LocalDate start_date;

    @NotNull(message = "종료 날짜는 필수입니다.")
    private LocalDate end_date;

    @NotBlank(message = "코스 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "코스 요약은 필수입니다.")
    private String summary;

    private String reference;

    private String instructor;
}
