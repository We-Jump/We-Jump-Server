package wejump.server.api.dto.course.lesson;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
public class LessonRequestDTO {

    @NotNull(message = "week는 필수입니다.")
    @Positive(message = "week는 양수이어야 합니다.")
    private Integer week;

    private LocalDate start;

    private String video;
}
