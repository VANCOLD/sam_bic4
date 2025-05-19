package fh.technikum.carsharing.services;

import fh.technikum.carsharing.persistence.entity.User;
import fh.technikum.carsharing.persistence.entity.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
@SpringBootTest
public class DtoTransformerServiceTest {

    @Autowired
    DtoTransformerService dtoTransformerService;
    
    @Test
    public void transformationDtoToModelRegular() {
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("user1");
        userDto.setPassword("test1234");
        userDto.setAge(21);
        userDto.setFirstName("Patrick");
        userDto.setSurname("Sommer");
        userDto.setFleetManager(false);
        userDto.setDrivingLicenseNumber(1234);
        userDto.setCreditCardNumber(4321);

        User output = dtoTransformerService.transformToModel(userDto, User.class);

        assertNotNull(output);
        assertEquals(1L, output.getUserId());
        assertEquals("user1", output.getUsername());
        assertEquals("test1234", output.getPassword());
        assertEquals(21, output.getAge());
        assertEquals("Patrick", output.getFirstName());
        assertEquals("Sommer", output.getSurname());
        assertFalse(output.getFleetManager());
        assertEquals(1234, output.getDrivingLicenseNumber());
        assertEquals(4321, output.getCreditCardNumber());
    }

    @Test
    public void transformationModelToDtoRegular() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("user1");
        user.setPassword("test1234");
        user.setAge(21);
        user.setFirstName("Patrick");
        user.setSurname("Sommer");
        user.setFleetManager(false);
        user.setDrivingLicenseNumber(1234);
        user.setCreditCardNumber(4321);

        UserDto output = dtoTransformerService.transformToDto(user, UserDto.class);

        assertNotNull(output);
        assertEquals(1L, output.getUserId());
        assertEquals("user1", output.getUsername());
        assertEquals("test1234", output.getPassword());
        assertEquals(21, output.getAge());
        assertEquals("Patrick", output.getFirstName());
        assertEquals("Sommer", output.getSurname());
        assertFalse(output.getFleetManager());
        assertEquals(1234, output.getDrivingLicenseNumber());
        assertEquals(4321, output.getCreditCardNumber());
    }

    @Test
    public void transformationDtoToModelNull() {
        UserDto userDto = new UserDto();
        User output = dtoTransformerService.transformToModel(userDto, User.class);

        assertNotNull(output);
        assertNull(output.getUserId());
        assertNull(output.getUsername());
        assertNull(output.getPassword());
        assertNull(output.getAge());
        assertNull(output.getFirstName());
        assertNull(output.getSurname());
        assertNull(output.getFleetManager());
        assertNull(output.getDrivingLicenseNumber());
        assertNull(output.getCreditCardNumber());
    }

    @Test
    public void transformationModelToDtoNull() {
        User user = new User();
        UserDto output = dtoTransformerService.transformToDto(user, UserDto.class);

        assertNotNull(output);
        assertNull(output.getUserId());
        assertNull(output.getUsername());
        assertNull(output.getPassword());
        assertNull(output.getAge());
        assertNull(output.getFirstName());
        assertNull(output.getSurname());
        assertNull(output.getFleetManager());
        assertNull(output.getDrivingLicenseNumber());
        assertNull(output.getCreditCardNumber());
    }

    @Test
    public void transformationDtoToModelPartial() {
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("user1");
        userDto.setAge(21);

        User output = dtoTransformerService.transformToModel(userDto, User.class);

        assertNotNull(output);
        assertEquals(1L, output.getUserId());
        assertEquals("user1", output.getUsername());
        assertEquals(21, output.getAge());

        assertNull(output.getPassword());
        assertNull(output.getFirstName());
        assertNull(output.getSurname());
        assertNull(output.getFleetManager());
        assertNull(output.getDrivingLicenseNumber());
        assertNull(output.getCreditCardNumber());
    }

    @Test
    public void transformationModelToDtoPartial() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("user1");
        user.setAge(21);

        UserDto output = dtoTransformerService.transformToDto(user, UserDto.class);

        assertNotNull(output);
        assertEquals(1L, output.getUserId());
        assertEquals("user1", output.getUsername());
        assertEquals(21, output.getAge());

        assertNull(output.getPassword());
        assertNull(output.getFirstName());
        assertNull(output.getSurname());
        assertNull(output.getFleetManager());
        assertNull(output.getDrivingLicenseNumber());
        assertNull(output.getCreditCardNumber());
    }

}
