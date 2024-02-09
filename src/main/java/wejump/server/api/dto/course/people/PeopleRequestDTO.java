package wejump.server.api.dto.course.people;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PeopleRequestDTO {
    @NotNull(message = "attendance는 필수입니다.")
    private String attendance;

    @NotNull(message = "evaluation은 필수입니다.")
    private String evaluation;

    @NotNull(message = "memberId는 필수입니다.")
    private Long memberId;

    @NotNull(message = "lessonId는 필수입니다.")
    private Long lessonId;

    @NotNull(message = "assignmentId는 필수입니다.")
    private Long assignmentId;
}
