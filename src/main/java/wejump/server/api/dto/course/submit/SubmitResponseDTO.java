package wejump.server.api.dto.course.submit;

import lombok.Builder;
import lombok.Getter;
import wejump.server.domain.assignment.SubmitId;

import java.time.LocalDateTime;

@Getter
@Builder
public class SubmitResponseDTO {
    private SubmitId submitId;

    private String name;

    private String filename;

    private LocalDateTime submissionTime;

    private String comment;
}
