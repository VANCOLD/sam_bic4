package fh.technikum.carsharing.persistence.entity.dto;

import fh.technikum.carsharing.persistence.entity.enums.Priority;
import lombok.Data;

@Data
public class EmergencyStatusDto {

    private Boolean isOccupied;
    private Long driverId;
    private Priority priority;
    private String emergencyDescription;
}
