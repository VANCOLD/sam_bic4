package fh.technikum.carsharing.persistence;

import fh.technikum.carsharing.persistence.entity.User;
import fh.technikum.carsharing.persistence.entity.Vehicle;
import fh.technikum.carsharing.persistence.entity.enums.Priority;
import fh.technikum.carsharing.persistence.repository.UserRepository;
import fh.technikum.carsharing.persistence.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Test
    @DirtiesContext
    void findByUsername() {

        User user = new User();
        user.setUsername("user1");
        user.setPassword("$2a$12$52o745P.4p1EVEra27kRwOpFmpqyMgfITPFjYcFInAQ2d50e3X/Pm");
        user.setAge(21);
        user.setFirstName("Patrick");
        user.setSurname("Sommer");
        user.setFleetManager(true);
        user.setDrivingLicenseNumber(1234);
        user.setCreditCardNumber(4321);

        var savedUser = this.userRepository.saveAndFlush(user);
        var foundUser = this.userRepository.findByUsername(user.getUsername());
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    @DirtiesContext
    void findByToken() {

        Vehicle vehicle = new Vehicle();
        vehicle.setTimeSinceLastUpdate(20.0);
        vehicle.setPriority(Priority.CRITICAL);
        vehicle.setCurrentTimestamp(LocalDateTime.now());
        vehicle.setDistanceSinceLastUpdate(40.0);
        vehicle.setDriverId(1L);
        vehicle.setEmergencyDescription("");
        vehicle.setIsOccupied(true);
        vehicle.setLatitude(10.5);
        vehicle.setLongitude(5.5);

        var savedVehicle = vehicleRepository.saveAndFlush(vehicle);
        var foundVehicle = vehicleRepository.findById(savedVehicle.getVehicleId()).get(); // ansich no go, aber ich habe keine token methode!?
        assertThat(foundVehicle).isEqualTo(savedVehicle);
    }

}
