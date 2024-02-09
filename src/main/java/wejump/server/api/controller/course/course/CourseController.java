package wejump.server.api.controller.course.course;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wejump.server.api.dto.course.course.CourseInfoResponseDTO;
import wejump.server.api.dto.course.course.CourseRequestDTO;
import wejump.server.api.dto.course.course.CourseResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.service.course.course.CourseService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        CourseResponseDTO createdCourse = courseService.createCourse(courseRequestDTO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }


    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseRequestDTO courseRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 검증 오류가 있는 경우 처리
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        CourseResponseDTO updatedCourse = courseService.updateCourse(courseId, courseRequestDTO);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }


    @GetMapping("/{courseId}")
    public CourseInfoResponseDTO getCourseInfo(@PathVariable Long courseId){
        Course course = courseService.getCourseById(courseId);
        return courseService.createCourseInfoResponseDTO(course);
    }
}