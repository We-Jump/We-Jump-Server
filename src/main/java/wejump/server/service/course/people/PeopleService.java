package wejump.server.service.course.people;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.api.dto.course.people.PeopleRequestDTO;
import wejump.server.api.dto.course.people.StatusDTO;
import wejump.server.api.dto.course.people.PeopleResponseDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Evaluate;
import wejump.server.domain.assignment.EvaluateId;
import wejump.server.domain.lesson.Attend;
import wejump.server.domain.lesson.AttendId;
import wejump.server.domain.lesson.Lesson;
import wejump.server.repository.course.assignment.EvaluateRepository;
import wejump.server.repository.course.lesson.LessonRepository;
import wejump.server.repository.course.lesson.AttendRepository;
import wejump.server.service.course.assignment.EvaluateService;
import wejump.server.service.course.lesson.AttendService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleService {

    private final LessonRepository lessonRepository;
    private final AttendRepository attendRepository;
    private final EvaluateRepository evaluateRepository;

    private final AttendService attendService;
    private final EvaluateService evaluateService;

    @Transactional(readOnly = true)
    public List<PeopleResponseDTO> getAllPeopleById(Long courseId){

        List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByStartAsc(courseId);
        List<PeopleResponseDTO> peopleResponseDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            List<Attend> attends = attendRepository.findAllByLessonIdOrderByName(lesson.getId());

            Assignment assignment = lesson.getAssignment();
            List<Evaluate> evaluates;
            if (assignment != null) {
                evaluates = evaluateRepository.findAllByAssignmentIdOrderByName(assignment.getId());
            } else {
                evaluates = Collections.emptyList();
            }

            if (!attends.isEmpty() && !evaluates.isEmpty()){
                List<StatusDTO> statusDTOS = IntStream.range(0, attends.size())
                        .mapToObj(i -> {
                            Attend attend = attends.get(i);
                            Evaluate evaluate = evaluates.get(i);

                            return StatusDTO.builder()
                                    .name(attend.getName())
                                    .attendance(attend.getAttendance())
                                    .evaluation(evaluate.getEvaluation())
                                    .memberId(attend.getMember().getId())
                                    .lessonId(lesson.getId())
                                    .assignmentId(assignment.getId())
                                    .build();
                        })
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();

                peopleResponseDTOS.add(peopleResponseDTO);
            } else if (attends.isEmpty() && !evaluates.isEmpty()) {
                List<StatusDTO> statusDTOS = evaluates.stream()
                        .map(evaluate -> StatusDTO.builder()
                                .name(evaluate.getName())
                                .evaluation(evaluate.getEvaluation())
                                .memberId(evaluate.getMember().getId())
                                .assignmentId(assignment.getId())
                                .build())
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();

                peopleResponseDTOS.add(peopleResponseDTO);
            } else if (!attends.isEmpty() && evaluates.isEmpty()) {
                List<StatusDTO> statusDTOS = attends.stream()
                        .map(attend -> StatusDTO.builder()
                                .name(attend.getName())
                                .attendance(attend.getAttendance())
                                .memberId(attend.getMember().getId())
                                .lessonId(lesson.getId())
                                .build())
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();

                peopleResponseDTOS.add(peopleResponseDTO);
            }
        }
        return peopleResponseDTOS;
    }


    public List<PeopleResponseDTO> getPeopleById(Long courseId, Long memberId){

        List<Lesson> lessons = lessonRepository.findAllByCourseIdOrderByStartAsc(courseId);
        List<PeopleResponseDTO> peopleResponseDTOS = new ArrayList<>();

        for (Lesson lesson : lessons) {
            List<Attend> attends = attendRepository.findAllByLessonIdAndMemberId(lesson.getId(), memberId);

            Assignment assignment = lesson.getAssignment();
            List<Evaluate> evaluates;
            if (assignment != null) {
                evaluates = evaluateRepository.findAllByAssignmentIdAndMemberId(assignment.getId(), memberId);
            } else {
                evaluates = Collections.emptyList();
            }

            if (!attends.isEmpty() && !evaluates.isEmpty()){
                List<StatusDTO> statusDTOS = IntStream.range(0, attends.size())
                        .mapToObj(i -> {
                            Attend attend = attends.get(i);
                            Evaluate evaluate = evaluates.get(i);

                            return StatusDTO.builder()
                                    .name(attend.getName())
                                    .attendance(attend.getAttendance())
                                    .evaluation(evaluate.getEvaluation())
                                    .memberId(attend.getMember().getId())
                                    .lessonId(lesson.getId())
                                    .assignmentId(assignment.getId())
                                    .build();
                        })
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();
                peopleResponseDTOS.add(peopleResponseDTO);

            } else if (attends.isEmpty() && !evaluates.isEmpty()) {
                List<StatusDTO> statusDTOS = evaluates.stream()
                        .map(evaluate -> StatusDTO.builder()
                                .name(evaluate.getName())
                                .evaluation(evaluate.getEvaluation())
                                .memberId(evaluate.getMember().getId())
                                .assignmentId(assignment.getId())
                                .build())
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();

                peopleResponseDTOS.add(peopleResponseDTO);
            } else if (!attends.isEmpty() && evaluates.isEmpty()) {
                List<StatusDTO> statusDTOS = attends.stream()
                        .map(attend -> StatusDTO.builder()
                                .name(attend.getName())
                                .attendance(attend.getAttendance())
                                .memberId(attend.getMember().getId())
                                .lessonId(lesson.getId())
                                .build())
                        .collect(Collectors.toList());

                PeopleResponseDTO peopleResponseDTO = PeopleResponseDTO.builder()
                        .week(lesson.getWeek())
                        .date(lesson.getStart())
                        .statusDTOS(statusDTOS)
                        .build();

                peopleResponseDTOS.add(peopleResponseDTO);
            }
        }
        return peopleResponseDTOS;
    }


    @Transactional
    public void updatePeople(List<PeopleRequestDTO> statusDTOS){
        statusDTOS.forEach(statusDTO -> {
                    AttendId attendId = new AttendId(statusDTO.getLessonId(), statusDTO.getMemberId());
                    attendService.updateAttend(attendId, statusDTO.getAttendance());

                    EvaluateId evaluateId = new EvaluateId(statusDTO.getAssignmentId(), statusDTO.getMemberId());
                    evaluateService.updateEvaluate(evaluateId, statusDTO.getEvaluation());
                });
    }
}


