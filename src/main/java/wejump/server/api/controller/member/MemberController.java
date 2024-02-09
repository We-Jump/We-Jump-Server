package wejump.server.api.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.api.dto.member.MyPageResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.member.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MemberController {

    private final CourseRepository courseRepository;

    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<MyPageResponseDTO> getAllCourses(@RequestParam("memberId") Long id) {
        LocalDate currentDate = LocalDate.now();
        Member member = memberRepository.findById(id).get();

        List<Course> allCourses = courseRepository.findAll();
        List<EnrollCourse> enrolledCourses = member.getEnrolledCourses();
        List<Course> myCourses = new ArrayList<>();

        for (EnrollCourse course : enrolledCourses) {
            myCourses.add(course.getCourse());
        }


        List<CourseResponseDTO> allCourseResponseDTOs = new ArrayList<>();
        for (Course course : allCourses) {
            if (!course.getEnd_date().isBefore(currentDate)) {
                CourseResponseDTO courseResponseDTO = CourseResponseDTO.builder()
                        .name(course.getName())
                        .startDate(course.getStart_date())
                        .endDate(course.getEnd_date())
                        .description(course.getDescription())
                        .summary(course.getSummary())
                        .reference(course.getReference())
                        .instructor(course.getInstructor().getName())
                        .image(course.getImage())
                        .build();

                allCourseResponseDTOs.add(courseResponseDTO);
            }
        }

        List<CourseResponseDTO> myCourseResponseDTOs = new ArrayList<>();
        for (Course course : myCourses) {
            if (!course.getEnd_date().isBefore(currentDate)) {
                CourseResponseDTO courseResponseDTO = CourseResponseDTO.builder()
                        .name(course.getName())
                        .startDate(course.getStart_date())
                        .endDate(course.getEnd_date())
                        .description(course.getDescription())
                        .summary(course.getSummary())
                        .reference(course.getReference())
                        .instructor(course.getInstructor().getName())
                        .image(course.getImage())
                        .build();

                myCourseResponseDTOs.add(courseResponseDTO);
            }
        }


        MyPageResponseDTO dto = MyPageResponseDTO.builder()
                .courses(allCourseResponseDTOs)
                .myCourses(myCourseResponseDTOs)
                .build();

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
