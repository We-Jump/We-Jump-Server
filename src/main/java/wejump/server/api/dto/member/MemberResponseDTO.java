package wejump.server.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wejump.server.domain.member.Member;

@Getter
@AllArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
}
