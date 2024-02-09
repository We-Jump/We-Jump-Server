package wejump.server.api.controller.course.courseMaterial;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.api.dto.course.submit.SubmitResponseDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Submit;
import wejump.server.domain.assignment.SubmitId;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.assignment.SubmitRepository;
import wejump.server.repository.member.MemberRepository;
import wejump.server.service.course.assignment.AssignmentService;
import wejump.server.service.course.assignment.SubmitService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses/submits/{assignmentId}")
@CrossOrigin
public class SubmitController {

    private final SubmitService submitService;
    private final AssignmentService assignmentService;
    private final MemberRepository memberRepository;

    private final SubmitRepository submitRepository;

    @PostMapping("/{memberId}")
    public ResponseEntity<Object> createSubmit(
            @PathVariable Long assignmentId,
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("comment") String comment
    ) throws IOException {

        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        Member member = memberRepository.findById(memberId).get();

        Submit submit = submitService.createSubmit(member, assignment, file, comment);

        return new ResponseEntity<>("submit success", HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public SubmitResponseDTO getSubmit(@PathVariable Long assignmentId,
                                       @PathVariable Long memberId){

        Submit submit = submitService.getSubmitById(assignmentId, memberId);
        if (submit != null){
            SubmitResponseDTO submitResponseDTO = submitService.createSubmitResponseDTO(submit);
            return submitResponseDTO;
        }

        return null;
    }


    //모든 제출 조회
    @GetMapping
    public List<SubmitResponseDTO> getAllSubmit(@PathVariable Long assignmentId){

        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        List<Submit> submits = assignment.getSubmits();

        if (!submits.isEmpty()){
            List<SubmitResponseDTO> submitResponseDTOS= assignment.getSubmits().stream()
                    .map(submitService::createSubmitResponseDTO)
                    .collect(Collectors.toList());

            return submitResponseDTOS;
        }
        return null;
    }

    @GetMapping("/{memberId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long assignmentId,
                                                 @PathVariable Long memberId) throws MalformedURLException {

        Submit submit = submitService.getSubmitById(assignmentId, memberId);

        if (submit == null){
            throw new IllegalArgumentException("Not Found, submit");
        }
        
        String filePath = submit.getFilePath();
        String fileName = extractFileName(filePath);

        Resource resource = new UrlResource("file:" + filePath);

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 파일 이름 추출
    private String extractFileName(String filePath) {
        int lastIndex = filePath.lastIndexOf("/");
        if (lastIndex != -1) {
            return filePath.substring(lastIndex + 1);
        } else {
            return filePath;
        }
    }

    @DeleteMapping("/{memberId}")
    public void deleteFile(@PathVariable Long assignmentId, @PathVariable Long memberId) {

        submitRepository.deleteByAssignmentIdAndMemberId(assignmentId, memberId);
    }

    private boolean deleteFile(String filePath) throws IOException {
        // 파일 삭제 로직
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
