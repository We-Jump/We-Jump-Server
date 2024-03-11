package wejump.server.api.controller.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.api.dto.member.MyPageResponseDTO;
import wejump.server.service.member.MemberService;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses(@RequestParam("memberId") Long id) {
        return new ResponseEntity<>(memberService.getAllCourses(id), HttpStatus.OK);
    }
}
