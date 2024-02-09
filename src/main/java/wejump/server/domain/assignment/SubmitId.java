package wejump.server.domain.assignment;

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
public class SubmitId implements Serializable {
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "member_id")
    private Long memberId;
}
