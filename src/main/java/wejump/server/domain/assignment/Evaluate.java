package wejump.server.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import wejump.server.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name = "evaluate")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate implements Serializable {

    @EmbeddedId
    private EvaluateId id;

    @Column
    private String name;

    @Column
    private Integer week;

    @Column
    private String evaluation;

    @ManyToOne
    @MapsId("assignmentId")
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;


    public void updateEvaluate(String evaluation){this.evaluation = evaluation;}
}
