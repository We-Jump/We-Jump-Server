package wejump.server.api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.CookieGenerator;
import wejump.server.api.dto.member.MemberResponseDTO;
import wejump.server.domain.member.Member;
import wejump.server.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class OAuthController {

    private final MemberRepository memberRepository;
    private static final String DEFAULT_ROLE = "Student";
    private static final String FRONTEND_REDIRECT_URL = "http://localhost:5173/weJump_Fe/";

    @GetMapping("/loginInfo")
    public ResponseEntity<MemberResponseDTO> oauthLoginInfo(
            Authentication authentication,
            HttpServletResponse response
    ) {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = attributes.get("email").toString();

            Member member;

            if (memberRepository.findByEmail(email).isPresent()) {
                member = memberRepository.findByEmail(email).get();
            } else {
                member = Member.builder()
                        .name(attributes.get("name").toString())
                        .email(email)
                        .role(DEFAULT_ROLE)
                        .build();
                memberRepository.save(member);
            }

            MemberResponseDTO memberDTO = new MemberResponseDTO(
                    member.getId(), member.getName(), member.getEmail(), member.getRole());

            ObjectMapper objectMapper = new ObjectMapper();
            String memberDTOJson = objectMapper.writeValueAsString(memberDTO);
            String base64EncodedMemberDTO = Base64.getEncoder().encodeToString(memberDTOJson.getBytes());

            CookieGenerator cookieGenerator = new CookieGenerator();
            cookieGenerator.setCookieName("MEMBERDTO");
            cookieGenerator.setCookiePath("/");
            cookieGenerator.setCookieMaxAge(60 * 60 * 24); // 1 day
            cookieGenerator.setCookieSecure(false);
            cookieGenerator.setCookieHttpOnly(false);

            Cookie cookie = new Cookie("MEMBERDTO", base64EncodedMemberDTO);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setSecure(false);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", FRONTEND_REDIRECT_URL);
            return new ResponseEntity<>(memberDTO, headers, HttpStatus.SEE_OTHER);
        } catch (Exception e) {
            log.error("OAuth login info error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
