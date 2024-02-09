package wejump.server.domain.announcement;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import wejump.server.api.dto.course.announcement.AnnouncementRequestDTO;
import wejump.server.domain.course.Course;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(name="announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    public void patch(AnnouncementRequestDTO dto) {

        if(dto.getTitle() != null){
            this.title = dto.getTitle();
        }
        if(dto.getContent() != null) {
            this.content = dto.getContent();
        }
    }

    public static Announcement createAnnouncement(AnnouncementRequestDTO dto, Course course) {
        // 예외 발생
        if (dto.getAnnouncementId() != null)
            throw new IllegalArgumentException("공지 생성 실패! 공지의 id가 없어야 합니다.");
        // 엔티티 생성 및 반환
        return Announcement.builder().
                id(dto.getAnnouncementId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(dto.getAnnouncementDate())
                .course(course)
                .build();
    }
}
