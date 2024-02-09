package wejump.server.api.controller.course.people;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import wejump.server.api.dto.course.people.PeopleRequestDTO;
import wejump.server.api.dto.course.people.PeopleResponseDTO;
import wejump.server.domain.course.Course;
import wejump.server.domain.member.Member;
import wejump.server.service.course.people.PeopleService;
import wejump.server.service.course.course.CourseService;
import wejump.server.service.member.MemberService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("courses/people")
@RequiredArgsConstructor
@CrossOrigin
public class PeopleController {

    private final CourseService courseService;
    private final MemberService memberService;
    private final PeopleService peopleService;

    // read all attendance and assignment
    @GetMapping("/{courseId}")
    public List<PeopleResponseDTO> getAllPeopleById(@PathVariable Long courseId){

        Course course = courseService.getCourseById(courseId);

        return peopleService.getAllPeopleById(courseId);
    }

    @GetMapping("/{courseId}/{memberId}")
    public List<PeopleResponseDTO> getPeopleById(@PathVariable Long courseId,
                                           @PathVariable Long memberId){

        Course course = courseService.getCourseById(courseId);
        Member member = memberService.getMemberById(memberId);

        return peopleService.getPeopleById(courseId, memberId);
    }

    @PutMapping
    public ResponseEntity<Object> updatePeople(@RequestBody @Valid List<PeopleRequestDTO> peopleRequestDTOS,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        peopleService.updatePeople(peopleRequestDTOS);
        return new ResponseEntity<>("update people success", HttpStatus.OK);
    }
}
