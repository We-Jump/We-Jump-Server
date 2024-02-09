package wejump.server.service.course.course;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.course.CourseInfoResponseDTO;
import wejump.server.api.dto.course.course.CourseRequestDTO;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.member.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO) {

        Member member = memberRepository.findByEmail(courseRequestDTO.getInstructor()).get();

        Course course = Course.builder()
                .name(courseRequestDTO.getName())
                .start_date(courseRequestDTO.getStart_date())
                .end_date(courseRequestDTO.getEnd_date())
                .description(courseRequestDTO.getDescription())
                .summary(courseRequestDTO.getSummary())
                .reference(courseRequestDTO.getReference())
                .instructor(member)
                .build();

        // 필수 필드 확인
        validateCourseFields(course);
        courseRepository.save(course);

        CourseResponseDTO courseResponseDTO = CourseResponseDTO.builder()
                .name(course.getName())
                .startDate(course.getStart_date())
                .endDate(course.getEnd_date())
                .description(course.getDescription())
                .summary(course.getSummary())
                .reference(course.getReference())
                .instructor(member.getName())
                .build();

        // 코스 저장
        return courseResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        // 엔티티를 DTO로 변환
        return courses.stream()
                .map(course -> course.build(course))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find course"));
        return course;
    }


    @Transactional
    public CourseResponseDTO updateCourse(Long courseId, CourseRequestDTO courseRequestDTO) {
        // courseId로 기존 코스를 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));

        Member member = memberRepository.findByName(courseRequestDTO.getInstructor()).get();

        // 기존 코스 정보 업데이트
        course.updateCourseInfo(courseRequestDTO.getName(),
                courseRequestDTO.getStart_date(),
                courseRequestDTO.getEnd_date(),
                courseRequestDTO.getDescription(),
                courseRequestDTO.getSummary(),
                courseRequestDTO.getReference(),
                member);

        courseRepository.save(course);

        CourseResponseDTO courseResponseDTO = CourseResponseDTO.builder()
                .name(course.getName())
                .startDate(course.getStart_date())
                .endDate(course.getEnd_date())
                .description(course.getDescription())
                .summary(course.getSummary())
                .reference(course.getReference())
                .instructor(member.getName())
                .build();

        // 코스 저장
        return courseResponseDTO;
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        // courseId로 기존 코스를 조회
        Course courseToDelete = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));

        // 코스 삭제
        courseRepository.delete(courseToDelete);
    }

    private void validateCourseFields(Course course) {
        if (course.getName() == null || course.getName().isEmpty()) {
            throw new IllegalArgumentException("코스 이름은 필수입니다.");
        }
        if (course.getStart_date() == null || course.getEnd_date() == null) {
            throw new IllegalArgumentException("시작 날짜와 종료 날짜는 필수입니다.");
        }
        if (course.getDescription() == null || course.getDescription().isEmpty()) {
            throw new IllegalArgumentException("코스 설명은 필수입니다.");
        }
        if (course.getSummary() == null || course.getSummary().isEmpty()) {
            throw new IllegalArgumentException("코스 요약은 필수입니다.");
        }
    }


    public CourseInfoResponseDTO createCourseInfoResponseDTO(Course course){
        return CourseInfoResponseDTO.builder()
                .name(course.getName())
                .instructorName(course.getInstructor().getName())
                .summary(course.getSummary())
                .description(course.getDescription())
                .reference(course.getReference())
                .build();
    }

}
