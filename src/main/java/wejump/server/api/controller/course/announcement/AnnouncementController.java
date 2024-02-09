package wejump.server.api.controller.course.announcement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.announcement.AnnouncementRequestDTO;
import wejump.server.api.dto.course.announcement.AnnouncementResponseDTO;
import wejump.server.service.course.announcement.AnnouncementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/courses/{courseId}/announcements") //코스별 notice, /courses/{courseId}/notice
@CrossOrigin
public class AnnouncementController {

    private final AnnouncementService announcementService;

    //모든 notice 조회
    @GetMapping("")
    public ResponseEntity<List<AnnouncementResponseDTO>> index(@PathVariable Long courseId){
        log.info(courseId.toString());
        log.info("test");
        return new ResponseEntity<>(announcementService.index(courseId), HttpStatus.OK);
    }

    //특정 notice 조회
    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponseDTO> show(@PathVariable Long announcementId) {
        log.info("read");
        return new ResponseEntity<>(announcementService.show(announcementId), HttpStatus.OK);
    }

    //특정 notice 삭제
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponseDTO> delete(@PathVariable Long announcementId) {
        AnnouncementResponseDTO deleted = announcementService.delete(announcementId);

        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //특정 notice 수정
    @PatchMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponseDTO> update(@PathVariable Long announcementId, @RequestBody AnnouncementRequestDTO dto) {
        AnnouncementResponseDTO updated = announcementService.update(announcementId, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //notice 등록
    @PostMapping("")
    public ResponseEntity<AnnouncementResponseDTO> create(@RequestBody AnnouncementRequestDTO dto, @PathVariable Long courseId) {
        log.info("create");
        AnnouncementResponseDTO created = announcementService.create(dto, courseId);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
