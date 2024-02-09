package wejump.server.domain.assignment;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.domain.member.Member;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "submit")
public class Submit implements Serializable {

    @EmbeddedId
    private SubmitId id;

    @Column(name = "file_path") // 파일 경로
    private String filePath;

    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assignmentId")
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
