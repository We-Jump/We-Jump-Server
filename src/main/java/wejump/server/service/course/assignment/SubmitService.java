package wejump.server.service.course.assignment;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wejump.server.api.dto.course.submit.SubmitResponseDTO;
import wejump.server.domain.assignment.Assignment;
import wejump.server.domain.assignment.Submit;
import wejump.server.domain.assignment.SubmitId;
import wejump.server.domain.member.Member;
import wejump.server.repository.course.assignment.AssignmentRepository;
import wejump.server.repository.course.assignment.SubmitRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import wejump.server.repository.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmitService {

    private final SubmitRepository submitRepository;
    private final MemberRepository memberRepository;
    private final AssignmentRepository assignmentRepository;
    @Value("${file.upload.path}")
    private String uploadPath;


    @Transactional
    public void createSubmit(Long memberId, Long assignmentId, MultipartFile file, String comment) throws IOException {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find member"));
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find Assignment"));
        // 업로드된 파일을 서버에 저장
        String fileName = file.getOriginalFilename();
        String filePath = saveFile(file);

        SubmitId submitId = new SubmitId(assignment.getId(), member.getId());
        Submit submit = Submit.builder()
                .id(submitId)
                .assignment(assignment)
                .member(member)
                .filePath(filePath)
                .submissionTime(LocalDateTime.now())
                .comment(comment)
                .build();
        submitRepository.save(submit);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // 경로 생성
        String fileName = file.getOriginalFilename();
        String filePath = uploadPath + "/" + fileName;

        // 파일 저장
        Path destPath = Path.of(filePath);
        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }


    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        log.info(filePath.toString());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileName);
        }
    }

    public void deleteSubmit(SubmitId submitId) {
        Submit submit = submitRepository.findById(submitId)
                .orElseThrow(() -> new IllegalArgumentException("제출을 찾을 수 없습니다."));
        submitRepository.delete(submit);
    }


    public SubmitResponseDTO getSubmitById(Long assignmentId, Long memberId){
       Submit submit =  submitRepository.findByAssignmentIdAndMemberId(assignmentId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find Assignment"));
        return SubmitResponseDTO.from(submit);
    }



    public SubmitResponseDTO createSubmitResponseDTO(Submit submit){
        String filePath = submit.getFilePath();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1);

        SubmitResponseDTO submitResponseDTO = SubmitResponseDTO.builder()
                .submitId(submit.getId())
                .name(submit.getMember().getName())
                .filename(fileName)
                .submissionTime(submit.getSubmissionTime())
                .comment(submit.getComment())
                .build();

        return submitResponseDTO;
    }
}
