package fh.technikum.carsharing.persistence.entity.dto;

import fh.technikum.carsharing.persistence.entity.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PriorityDto {
    /**
     * A string representation of the Priority Enum {@link Priority}
     */
    private String priority;
}
