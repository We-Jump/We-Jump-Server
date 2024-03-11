package wejump.server.service.member;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.api.dto.member.MyPageResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.member.MemberRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find member"));
        return member;
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses(final Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cannot find member"));

        List<Course> myCourses = member.getEnrolledCourses()
                .stream().map(EnrollCourse::getCourse).toList();;

        return myCourses.stream()
                .filter(course -> !course.getEnd_date().isBefore(LocalDate.now()))
                .map(Course::of).toList();
    }
}
