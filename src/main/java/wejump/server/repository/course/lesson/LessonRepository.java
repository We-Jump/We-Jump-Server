package wejump.server.repository.course.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wejump.server.domain.lesson.Lesson;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByCourseIdOrderByStartAsc(Long courseId);
    Lesson findByCourseIdAndStart(Long courseId, LocalDate start);
}
