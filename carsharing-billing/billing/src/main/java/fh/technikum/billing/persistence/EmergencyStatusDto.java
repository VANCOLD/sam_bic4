package fh.technikum.billing.persistence;

import fh.technikum.billing.persistence.enums.Priority;
import lombok.Data;

@Data
public class EmergencyStatusDto implements DataTransferObject {

    private Boolean isOccupied;
    private Long driverId;
    private Priority priority;
    private String emergencyDescription;
}

