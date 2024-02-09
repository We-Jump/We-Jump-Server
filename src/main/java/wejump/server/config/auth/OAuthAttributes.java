package wejump.server.config.auth;

import wejump.server.api.dto.member.MemberProfile;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {   //enum을 provider 형식에 맞춰 MemberProfile 형태로 만들어 반환
    GOOGLE("google", (attributes) -> {
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setName((String) attributes.get("name"));
        memberProfile.setEmail((String) attributes.get("email"));
        return memberProfile;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberProfile> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, MemberProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}