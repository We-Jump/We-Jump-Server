package wejump.server.repository.course.announcement;

import wejump.server.domain.announcement.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Override
    ArrayList<Announcement> findAll();

    Announcement findByCourseId(Long courseId);

    ArrayList<Announcement> findAllByCourseId(Long courseId);
}
