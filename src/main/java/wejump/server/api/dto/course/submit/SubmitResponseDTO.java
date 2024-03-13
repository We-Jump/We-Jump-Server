package wejump.server.api.dto.course.submit;

import lombok.Builder;
import lombok.Getter;
import wejump.server.domain.assignment.Submit;
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

    public static SubmitResponseDTO from(Submit submit){
        String filePath = submit.getFilePath();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

        return SubmitResponseDTO.builder()
                .submitId(submit.getId())
                .name(submit.getMember().getName())
                .filename(fileName)
                .submissionTime(submit.getSubmissionTime())
                .comment(submit.getComment())
                .build();
    }
}
