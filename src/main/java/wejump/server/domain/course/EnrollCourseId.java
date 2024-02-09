package wejump.server.domain.course;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EnrollCourseId implements Serializable {
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "course_id")
    private Long courseId;
}