package wejump.server.service.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.syllabus.SyllabusRequestDTO;
import wejump.server.api.dto.course.syllabus.SyllabusResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.Syllabus;
import wejump.server.repository.course.course.SyllabusRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SyllabusService {
    private final SyllabusRepository syllabusRepository;

    @Transactional
    public Syllabus createSyllabus(Course course, SyllabusRequestDTO syllabusRequestDTO){
        Syllabus syllabus = Syllabus.builder()
                .week(syllabusRequestDTO.getWeek())
                .title(syllabusRequestDTO.getTitle())
                .video(syllabusRequestDTO.getVideo())
                .assignment(syllabusRequestDTO.getAssignment())
                .course(course)
                .build();

        return syllabusRepository.save(syllabus);
    }

    @Transactional(readOnly = true)
    public Syllabus getSyllabusById(Long syllabusId){
        Syllabus syllabus = syllabusRepository.findById(syllabusId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find syllabus"));
        return syllabus;
    }


    @Transactional
    public Syllabus updateSyllabus (Syllabus syllabus, SyllabusRequestDTO syllabusRequestDTO){
        syllabus.updateSyllabusInfo(syllabusRequestDTO.getWeek(),
                syllabusRequestDTO.getTitle(),
                syllabusRequestDTO.getVideo(),
                syllabusRequestDTO.getAssignment());

        return syllabusRepository.save(syllabus);
    }

    @Transactional
    public void deleteSyllabus(Syllabus syllabus){
        syllabusRepository.delete(syllabus);
    }

    public SyllabusResponseDTO createSyllabusResponseDTO(Syllabus syllabus){
        return SyllabusResponseDTO.builder()
                .id(syllabus.getId())
                .week(syllabus.getWeek())
                .title(syllabus.getTitle())
                .video(syllabus.getVideo())
                .assignment(syllabus.getAssignment())
                .build();

    }
}