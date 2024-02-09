package wejump.server.domain.lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.course.Course;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@DynamicUpdate
@Entity
@Table(name = "lesson")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @Column(nullable = false)
    private Integer week;

    @Column
    private String video;

    @Column(nullable = true)
    private LocalDate start;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attend> inCourses;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @OneToOne(mappedBy = "lesson", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Assignment assignment;

    public void updateLessonInfo(Integer week, String video, LocalDate start) {
        this.week = week;
        this.video = video;
        this.start = start;
    }

}
