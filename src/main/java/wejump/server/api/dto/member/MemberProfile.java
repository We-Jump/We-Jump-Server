package wejump.server.api.dto.member;

import lombok.Getter;
import lombok.Setter;
import wejump.server.domain.member.Member;

@Getter
@Setter
public class MemberProfile {
    private String name;
    private String email;

    private String provider;

    private String nickname;

    private String image;

    public Member toMember() {
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }

}
