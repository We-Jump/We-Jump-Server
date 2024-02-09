package wejump.server.domain.lesson;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AttendId implements Serializable {
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "member_id")
    private Long memberId;
}
