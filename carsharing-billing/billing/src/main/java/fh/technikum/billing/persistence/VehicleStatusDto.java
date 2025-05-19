package fh.technikum.billing.persistence;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleStatusDto implements DataTransferObject {

    private Double longitude;
    private Double latitude;
    private LocalDateTime currentTimestamp;
    private Boolean isOccupied;
    private Long driverId;
    private Double distanceSinceLastUpdate;
    private Double timeSinceLastUpdate;
}
