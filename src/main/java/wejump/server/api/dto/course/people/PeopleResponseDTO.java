package wejump.server.api.dto.course.people;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Setter
public class PeopleResponseDTO {
    private Integer week;
    private LocalDate date;
    private List<StatusDTO> statusDTOS;
}
