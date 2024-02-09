package wejump.server.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.api.dto.course.assignment.AssignmentResponseDTO;
import wejump.server.domain.lesson.Lesson;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "assignment")
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "end")
    private LocalDate end;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submit> submits; // Assignment와 Submit 엔티티 간의 관계 설정


    @OneToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evaluate> evaluates;


    /*
     ****************************************비지니스 로직****************************************
     */

    //dto -> model로 변환 기능 포함
    public void updateAssignment(String title, String description, LocalDate end) {
            this.title = title;
            this.description = description;
            this.end = end;
    }
}
