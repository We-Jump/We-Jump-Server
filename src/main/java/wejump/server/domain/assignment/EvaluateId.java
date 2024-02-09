package wejump.server.domain.assignment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateId implements Serializable {

    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "member_id")
    private Long memberId;
}
