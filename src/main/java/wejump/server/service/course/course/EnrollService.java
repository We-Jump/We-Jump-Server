package wejump.server.service.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.course.EnrollResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.course.EnrollCourseId;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.course.course.EnrollRepository;
import wejump.server.repository.member.MemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollService {

    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final EnrollRepository enrollRepository;
    @Transactional
    public EnrollCourse enrollMemberToCourse(Course course, Member member) {
        LocalDate enrollDate = LocalDate.now();

        EnrollCourseId enrollCourseId = new EnrollCourseId(member.getId(), course.getId());
        EnrollCourse enrollCourse = EnrollCourse.builder()
                .id(enrollCourseId)
                .course(course)
                .member(member)
                .date(enrollDate)
                .accepted(false)
                .build();

        return enrollRepository.save(enrollCourse);
    }


    @Transactional(readOnly = true)
    public Boolean getEnrollStatus(Long courseId, Long memberId){

        EnrollCourseId enrollCourseId = new EnrollCourseId(memberId, courseId);
        EnrollCourse enrollCourse= enrollRepository.findById(enrollCourseId)
                .orElse(null);

        if (enrollCourse == null){
            return null;
        }

        return enrollCourse.getAccepted();
    }

    @Transactional(readOnly = true)
    public List<EnrollResponseDTO> getAcceptedEnrollCourses(Long courseId){
        return enrollRepository.findAllByCourseIdAndAcceptedTrue(courseId).stream()
                .map(this::createEnrollResonseDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<EnrollResponseDTO> getNotAcceptedEnrollCourses(Long courseId){
        return enrollRepository.findAllByCourseIdAndAcceptedFalse(courseId).stream()
                .map(this::createEnrollResonseDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public EnrollCourse updateEnroll(Long courseId, Long memberId){
        EnrollCourseId enrollCourseId = new EnrollCourseId(memberId, courseId);

        EnrollCourse existingEnroll = enrollRepository.findById(enrollCourseId)
                .orElseThrow(()-> new IllegalArgumentException("cannot find enrolls"));

        existingEnroll.updateEnroll();

        return enrollRepository.save(existingEnroll);
    }


    @Transactional
    public void deleteEnroll(Long courseId, Long memberId){
        EnrollCourseId enrollCourseId = new EnrollCourseId(memberId, courseId);

        EnrollCourse existingEnroll = enrollRepository.findById(enrollCourseId)
                .orElseThrow(()-> new IllegalArgumentException("cannot find enrolls"));

        enrollRepository.delete(existingEnroll);
    }


    public EnrollResponseDTO createEnrollResonseDTO(EnrollCourse enrollCourse){
        return EnrollResponseDTO.builder()
                .id(enrollCourse.getId())
                .name(enrollCourse.getMember().getName())
                .enrolledDate(enrollCourse.getDate())
                .accepted(enrollCourse.getAccepted())
                .build();
    }
}
