package wejump.server.domain.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.lesson.Lesson;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.domain.member.Member;
import wejump.server.domain.announcement.Announcement;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@DynamicUpdate
@Entity
@Getter
@Table(name = "course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start", nullable = true)
    private LocalDate start_date;

    @Column(name = "end", nullable = true)
    private LocalDate end_date;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "summary", nullable = true)
    private String summary;

    @Column(name = "reference", nullable = true)
    private String reference;

    @Column(name = "image", nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Syllabus> syllabuses = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnrollCourse> enrolledCourses = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements = new ArrayList<>();

    /*
     ****************************************비지니스 로직****************************************
     */

    // 비즈니스 로직을 통해 필드 값 변경
    
    public void updateCourseInfo(String name, LocalDate start_date, LocalDate end_date, String description, String summary,
                                 String reference, Member instructor) {

        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.summary = summary;
        this.reference = reference;
        this.instructor = instructor;
    }

    public CourseResponseDTO build(Course course) {

        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .startDate(course.getStart_date())
                .endDate(course.getEnd_date())
                .description(course.getDescription())
                .summary(course.getSummary())
                .reference(course.getReference())
                .image(course.getImage())
                .build();
    }
}
