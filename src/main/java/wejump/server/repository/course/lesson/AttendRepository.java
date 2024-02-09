package wejump.server.repository.course.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.AttendId;

import java.util.List;

@Repository
public interface AttendRepository extends JpaRepository<Attend, AttendId> {
    List<Attend> findAllByLessonIdOrderByName(Long lessonId);
    List<Attend> findAllByLessonIdAndMemberId(Long lessonId, Long memberId);
}
