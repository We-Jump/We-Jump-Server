package wejump.server.api.controller.course.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.syllabus.SyllabusRequestDTO;
import wejump.server.api.dto.course.syllabus.SyllabusResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.course.Syllabus;
import wejump.server.service.course.course.CourseService;
import wejump.server.service.course.course.SyllabusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("courses/syllabus")
@RequiredArgsConstructor
@CrossOrigin
public class SyllabusController {
    private final CourseService courseService;
    private final SyllabusService syllabusService;

    @PostMapping("/{courseId}")
    public ResponseEntity<Object> createCoursePlan(@PathVariable Long courseId,
                                                 @RequestBody @Valid SyllabusRequestDTO syllabusRequestDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Course course = courseService.getCourseById(courseId);
        Syllabus syllabus = syllabusService.createSyllabus(course, syllabusRequestDTO);

        return new ResponseEntity<>("create syllabus success!", HttpStatus.CREATED);
    }


    @GetMapping("/{courseId}")
    public List<SyllabusResponseDTO> getSyllabusByCourseId(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Syllabus> syllabuses = course.getSyllabuses();

        if (syllabuses.isEmpty()){ return null; }

        return syllabuses.stream()
                .map(syllabusService::createSyllabusResponseDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{syllabusId}")
    public ResponseEntity<Object> updateSyllabus(@PathVariable Long syllabusId,
                                                 @RequestBody @Valid SyllabusRequestDTO syllabusRequestDTO,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Syllabus syllabus = syllabusService.getSyllabusById(syllabusId);
        Syllabus updatedSyllabus = syllabusService.updateSyllabus(syllabus, syllabusRequestDTO);

        return new ResponseEntity<>("update syllabus success!", HttpStatus.OK);
    }

    @DeleteMapping("/{syllabusId}")
    public ResponseEntity<Object> deleteSyllabus(@PathVariable Long syllabusId){
        Syllabus syllabus = syllabusService.getSyllabusById(syllabusId);
        syllabusService.deleteSyllabus(syllabus);

        return new ResponseEntity<>("delete syllabus success!", HttpStatus.OK);
    }
}