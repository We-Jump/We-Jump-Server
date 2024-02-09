package wejump.server.domain.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Getter
@Table(name = "enroll_course")
@Builder
public class EnrollCourse implements Serializable {

    @EmbeddedId
    private EnrollCourseId id;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Builder.Default
    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id") // Member 엔티티의 외래 키
    private Member member;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id") // Course 엔티티의 외래 키
    private Course course;

    public void updateEnroll(){
        this.accepted = true;
    }

}
