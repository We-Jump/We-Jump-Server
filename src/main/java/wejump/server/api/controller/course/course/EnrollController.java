package wejump.server.api.controller.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.course.EnrollResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.member.Member;
import wejump.server.service.course.course.CourseService;
import wejump.server.service.course.course.EnrollService;
import wejump.server.service.member.MemberService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses/enroll/{courseId}")
@RequiredArgsConstructor
@CrossOrigin
public class EnrollController {

    private final CourseService courseService;
    private final MemberService memberService;
    private final EnrollService enrollService;

    //수강 신청
    @PostMapping("/{memberId}")
    public ResponseEntity<Object> enrollMemberToCourse(
            @PathVariable Long courseId,
            @PathVariable Long memberId
    ) {
        Course course = courseService.getCourseById(courseId);
        Member member = memberService.getMemberById(memberId);

        enrollService.enrollMemberToCourse(course, member);

        return new ResponseEntity<>("Member enrolled successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public Boolean getEnrollStatus(@PathVariable Long courseId,
                                             @PathVariable Long memberId){
        Course course = courseService.getCourseById(courseId);
        Member member = memberService.getMemberById(memberId);

        return enrollService.getEnrollStatus(courseId, memberId);
    }


    @GetMapping
    public Map<String, List<EnrollResponseDTO>> getAllEnrolls(@PathVariable Long courseId){

        Course course = courseService.getCourseById(courseId);

        List<EnrollResponseDTO> acceptedEnrollCourses = enrollService.getAcceptedEnrollCourses(courseId);
        List<EnrollResponseDTO> notAcceptedEnrollCourses = enrollService.getNotAcceptedEnrollCourses(courseId);

        Map<String, List<EnrollResponseDTO>> response = new HashMap<>();
        response.put("notAccepted", notAcceptedEnrollCourses);
        response.put("accepted", acceptedEnrollCourses);

        return response;
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Object> updateEnroll(@PathVariable Long courseId,
                                                @PathVariable Long memberId){
        Course course = courseService.getCourseById(courseId);
        Member member = memberService.getMemberById(memberId);

        EnrollCourse updatedEnroll = enrollService.updateEnroll(courseId, memberId);

        return new ResponseEntity<>("update enroll success", HttpStatus.OK);
    }


    @DeleteMapping("/{memberId}")
    public ResponseEntity<Object> deleteEnroll(@PathVariable Long courseId,
                                               @PathVariable Long memberId){
        Course course = courseService.getCourseById(courseId);
        Member member = memberService.getMemberById(memberId);

        enrollService.deleteEnroll(courseId, memberId);

        return new ResponseEntity<>("delete enroll success", HttpStatus.OK);
    }

}
