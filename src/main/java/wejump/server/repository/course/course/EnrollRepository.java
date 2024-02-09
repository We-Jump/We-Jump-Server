package wejump.server.repository.course.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.domain.course.EnrollCourseId;
import wejump.server.domain.member.Member;

import java.util.List;

@Repository
public interface EnrollRepository extends JpaRepository<EnrollCourse, EnrollCourseId> {
    List<EnrollCourse> findAllByCourseId(Long courseId);
    List<EnrollCourse> findAllByCourseIdAndAcceptedFalse(Long courseId);
    List<EnrollCourse> findAllByCourseIdAndAcceptedTrue(Long courseId);
}
