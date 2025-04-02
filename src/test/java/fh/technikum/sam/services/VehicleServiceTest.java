package fh.technikum.sam.services;

import fh.technikum.sam.models.Vehicle;
import fh.technikum.sam.models.dto.VehicleDto;
import fh.technikum.sam.models.enums.Priority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VehicleServiceTest {

    @Autowired
    VehicleService vehicleService;

    private static final Vehicle vehicle1 = new Vehicle();
    private static final Vehicle vehicle2 = new Vehicle();
    private static final VehicleDto vehicleDto1 = new VehicleDto();
    private static final VehicleDto vehicleDto2 = new VehicleDto();

    @BeforeAll
    public static void initializeData() {

        vehicle1.setTimeSinceLastUpdate(20.0);
        vehicle1.setPriority(Priority.CRITICAL);
        vehicle1.setCurrentTimestamp(LocalDateTime.now());
        vehicle1.setDistanceSinceLastUpdate(40.0);
        vehicle1.setDriverId(1);
        vehicle1.setEmergencyDescription("");
        vehicle1.setIsOccupied(true);
        vehicle1.setLatitude(10.5);
        vehicle1.setLongitude(5.5);
        vehicle1.setVehicleId(1L);

        vehicle2.setTimeSinceLastUpdate(1333.0);
        vehicle2.setPriority(Priority.MEDIUM);
        vehicle2.setCurrentTimestamp(LocalDateTime.now());
        vehicle2.setDistanceSinceLastUpdate(10.0);
        vehicle2.setEmergencyDescription("Jo jo");
        vehicle2.setIsOccupied(false);
        vehicle2.setLatitude(50.0);
        vehicle2.setLongitude(35.35);
        vehicle2.setVehicleId(2L);

        vehicleDto1.setTimeSinceLastUpdate(20.0);
        vehicleDto1.setPriority(Priority.CRITICAL);
        vehicleDto1.setCurrentTimestamp(LocalDateTime.now());
        vehicleDto1.setDistanceSinceLastUpdate(40.0);
        vehicleDto1.setDriverId(1);
        vehicleDto1.setEmergencyDescription("");
        vehicleDto1.setIsOccupied(true);
        vehicleDto1.setLatitude(10.5);
        vehicleDto1.setLongitude(5.5);
        vehicleDto1.setVehicleId(1L);

        vehicleDto2.setTimeSinceLastUpdate(1333.0);
        vehicleDto2.setPriority(Priority.MEDIUM);
        vehicleDto2.setCurrentTimestamp(LocalDateTime.now());
        vehicleDto2.setDistanceSinceLastUpdate(10.0);
        vehicleDto2.setEmergencyDescription("Jo jo");
        vehicleDto2.setIsOccupied(false);
        vehicleDto2.setLatitude(50.0);
        vehicleDto2.setLongitude(35.35);
        vehicleDto2.setVehicleId(2L);
    }

    @AfterEach
    public void tearDown() {
        vehicleService.reset();
    }



    @Test
    void getAllRegularTest() {
        vehicleService.create(vehicleDto1);
        vehicleService.create(vehicleDto2);

        List<Vehicle> vehicleList = vehicleService.getAll();
        assertThat(vehicleList)
                .usingRecursiveComparison()
                .ignoringFields( "currentTimestamp")
                .isEqualTo(List.of(vehicle1, vehicle2));
    }

    @Test
    void getAllEmptyTest() {
        List<Vehicle> vehicleList = vehicleService.getAll();
        assertThat(vehicleList).isEqualTo(List.of());
    }

    @Test
    void getByIdRegularTest() {
        Long createdVehicleId = vehicleService.create(vehicleDto1).getVehicleId();

        Vehicle vehicleToCheck = vehicleService.getById(createdVehicleId);
        assertThat(vehicleToCheck)
                .usingRecursiveComparison()
                .ignoringFields( "currentTimestamp")
                .isEqualTo(vehicle1);
    }

    @Test
    void getByIdWrongIdTest() {
        Vehicle vehicleToCheck = vehicleService.getById(10L);
        assertThat(vehicleToCheck).isNull();
    }

    @Test
    void createRegularTest() {
        Vehicle createdVehicle = vehicleService.create(vehicleDto1);
        assertThat(createdVehicle)
                .usingRecursiveComparison()
                .ignoringFields( "currentTimestamp")
                .isEqualTo(vehicle1);
    }


    @Test
    void createDuplicateTest() {
        Vehicle createdVehicle = vehicleService.create(vehicleDto1);
        assertThat(createdVehicle)
                .usingRecursiveComparison()
                .ignoringFields( "currentTimestamp")
                .isEqualTo(vehicle1);

        createdVehicle = vehicleService.create(vehicleDto1);
        assertThat(createdVehicle).isNull();
    }


    @Test
    void deleteByIdRegularTest() {
        Vehicle createdVehicle = vehicleService.create(vehicleDto1);
        Vehicle deletedVehicle = vehicleService.deleteById(createdVehicle.getVehicleId());

        assertThat(createdVehicle)
                .usingRecursiveComparison()
                .ignoringFields( "currentTimestamp")
                .isEqualTo(deletedVehicle);
    }

    @Test
    void deleteByIdInvalidIdTest() {
        Vehicle deletedVehicle = vehicleService.deleteById(10L);
        assertThat(deletedVehicle).isNull();
    }
}
