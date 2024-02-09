package wejump.server.domain.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Table(name = "syllabus")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Syllabus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "syllabus_id")
    private Long id;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video")
    private Boolean video;

    @Column(name = "assignment")
    private Boolean assignment;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void updateSyllabusInfo(Integer week, String title, Boolean video, Boolean assignment){
        this.week = week;
        this.title = title;
        this.video = video;
        this.assignment = assignment;
    }
}
