package wejump.server.service.course.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Evaluate;
import wejump.server.domain.assignment.EvaluateId;
import wejump.server.domain.course.EnrollCourse;
import wejump.server.repository.course.assignment.EvaluateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluateService {
    private final EvaluateRepository evaluateRepository;

    @Transactional
    public List<Evaluate> createEvaluate(Assignment assignment, List<EnrollCourse> enrollCourses){

        List<Evaluate> evaluates = enrollCourses.stream()
                .map(enrollCourse -> {
                    EvaluateId evaluateId = new EvaluateId(assignment.getId(), enrollCourse.getMember().getId());
                    return Evaluate.builder()
                            .id(evaluateId)
                            .name(enrollCourse.getMember().getName())
                            .week(assignment.getLesson().getWeek())
                            .assignment(assignment)
                            .member(enrollCourse.getMember())
                            .evaluation("unknown")
                            .build();
                })
                .collect(Collectors.toList());

        return evaluateRepository.saveAll(evaluates);
    }

    @Transactional
    public void updateEvaluate(EvaluateId evaluateId, String evaluation){
        Evaluate existingEvaluate = evaluateRepository.findById(evaluateId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find evalaute"));

        existingEvaluate.updateEvaluate(evaluation);
        evaluateRepository.save(existingEvaluate);
    }
}
