package wejump.server.api.dto.course.announcement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRequestDTO {

    private Long announcementId;
    private String title;
    private String content;
    private LocalDateTime announcementDate;
    private Long courseId;

}
