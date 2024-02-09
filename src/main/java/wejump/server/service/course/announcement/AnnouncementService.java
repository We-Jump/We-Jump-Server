package wejump.server.service.course.announcement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.announcement.AnnouncementRequestDTO;
import wejump.server.api.dto.course.announcement.AnnouncementResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.announcement.Announcement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wejump.server.repository.course.course.CourseRepository;
import wejump.server.repository.course.announcement.AnnouncementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CourseRepository courseRepository;

    public List<AnnouncementResponseDTO> index(Long courseId) {

        return announcementRepository.findAllByCourseId(courseId)
                .stream()
                .map(Notice -> AnnouncementResponseDTO.createAnnouncementResponseDto(Notice))
                .collect(Collectors.toList());
    }

    //없는 아이디 조회 시 빈 json, 응답코드 200
    public AnnouncementResponseDTO show(Long noticeId) {
        Announcement announcement = announcementRepository.findById(noticeId)
                .orElseThrow(()->new IllegalArgumentException("공지 조회 실패"));
        return AnnouncementResponseDTO.createAnnouncementResponseDto(announcement);
    }

    @Transactional
    public AnnouncementResponseDTO create(AnnouncementRequestDTO dto, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()->new IllegalArgumentException("공지 생성 실패, 코스를 찾을 수 없습니다"));
        dto.setAnnouncementDate(LocalDateTime.now()); // 생성시간 만들기
        log.info(dto.toString());
        Announcement announcement = Announcement.createAnnouncement(dto,course);
        Announcement created = announcementRepository.save(announcement);
        return AnnouncementResponseDTO.createAnnouncementResponseDto(created);
    }

    @Transactional
    public AnnouncementResponseDTO delete(Long id) {
        Announcement target = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지 삭제 실패, 대상이 없습니다."));
        announcementRepository.delete(target);
        return AnnouncementResponseDTO.createAnnouncementResponseDto(target);

    }

    @Transactional
    public AnnouncementResponseDTO update(Long id, AnnouncementRequestDTO dto) {

        Announcement target = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지 수정 실패! 대상 코스 없습니다."));
        target.patch(dto);
        Announcement updated = announcementRepository.save(target);
        return AnnouncementResponseDTO.createAnnouncementResponseDto(updated);
    }

}
