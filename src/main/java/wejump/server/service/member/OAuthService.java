package wejump.server.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import wejump.server.api.dto.member.MemberProfile;
import wejump.server.config.auth.OAuthAttributes;
import wejump.server.domain.member.Member;
import wejump.server.repository.member.MemberRepository;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override //access 토큰 기반 유저 정보 가지고 오기
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration() // OAuth 서비스 이름
                .getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration() // OAuth 로그인 시 pk
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        MemberProfile memberProfile = OAuthAttributes.extract(registrationId, attributes);
        memberProfile.setProvider(registrationId);
        Member member = saveOrUpdate(memberProfile);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, memberProfile, registrationId);

        return new DefaultOAuth2User(   //Oauth2.0 기본 구현
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, MemberProfile memberProfile, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", memberProfile.getName());
        customAttribute.put("email", memberProfile.getEmail());
        return customAttribute;

    }

    private Member saveOrUpdate(MemberProfile memberProfile) {

        Member member = memberRepository.findByEmail(memberProfile.getEmail())
                .map(m -> m.update(memberProfile.getName(), memberProfile.getEmail())) // OAuth 서비스 사이트에서 유저 정보 변경시 DB에도 update
                .orElse(memberProfile.toMember());

        return memberRepository.save(member);
    }
}
