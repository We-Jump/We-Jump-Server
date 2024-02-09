package wejump.server.domain.lesson;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@DynamicUpdate
@Entity
@Table(name = "attend")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attend implements Serializable {

    @EmbeddedId
    private AttendId id;

    @Column
    private String name;

    @Column
    private Integer week;

    @Column
    private String attendance;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAttend(String attendance) {
        this.attendance = attendance;
    }
}
