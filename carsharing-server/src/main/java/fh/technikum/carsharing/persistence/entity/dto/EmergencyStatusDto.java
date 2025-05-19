package fh.technikum.carsharing.persistence.entity.dto;

import fh.technikum.carsharing.persistence.entity.enums.Priority;
import lombok.Data;

@Data
public class EmergencyStatusDto implements DataTransferObject {

    private Boolean isOccupied;
    private Long driverId;
    private Priority priority;
    private String emergencyDescription;
}
