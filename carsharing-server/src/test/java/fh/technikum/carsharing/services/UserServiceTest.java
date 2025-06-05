package fh.technikum.carsharing.services;

import fh.technikum.carsharing.persistence.entity.User;
import fh.technikum.carsharing.persistence.entity.dto.LoginDto;
import fh.technikum.carsharing.persistence.entity.dto.UserDto;
import fh.technikum.carsharing.persistence.repository.UserRepository;
import fh.technikum.carsharing.utils.ResponseMessages;
import fh.technikum.carsharing.utils.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private static final UserDto userDto1 = new UserDto();
    private static final UserDto userDto2 = new UserDto();
    private static final User user1 = new User();
    private static final User user2 = new User();
    private static final LoginDto loginDto1 = new LoginDto();
    private static final LoginDto loginDto2 = new LoginDto();

    @BeforeEach
    public void initializeData() {

        userDto1.setUserId(1L);
        userDto1.setUsername("user1");
        userDto1.setPassword("test1234");
        userDto1.setAge(21);
        userDto1.setFirstName("Patrick");
        userDto1.setSurname("Sommer");
        userDto1.setFleetManager(true);
        userDto1.setDrivingLicenseNumber(1234);
        userDto1.setCreditCardNumber(4321);

        userDto2.setUserId(2L);
        userDto2.setUsername("test");
        userDto2.setPassword("abcdefg");
        userDto2.setAge(74);
        userDto2.setFirstName("Herbert");
        userDto2.setSurname("Hillie");
        userDto2.setFleetManager(false);
        userDto2.setDrivingLicenseNumber(441232311);
        userDto2.setCreditCardNumber(12342345);

        loginDto1.setUsername("user1");
        loginDto1.setPassword("test1234");
        loginDto2.setUsername("test");
        loginDto2.setPassword("abcdefg");
        

        user1.setUserId(1L);
        user1.setUsername("user1");
        user1.setPassword("$2a$12$52o745P.4p1EVEra27kRwOpFmpqyMgfITPFjYcFInAQ2d50e3X/Pm");
        user1.setAge(21);
        user1.setFirstName("Patrick");
        user1.setSurname("Sommer");
        user1.setFleetManager(true);
        user1.setDrivingLicenseNumber(1234);
        user1.setCreditCardNumber(4321);

        user2.setUserId(2L);
        user2.setUsername("test");
        user2.setPassword("$2a$12$/31VcOYYvLuunvOR4Sr6QepfAPps886ffs5XU/MV95GHVh1MeCVky");
        user2.setAge(74);
        user2.setFirstName("Herbert");
        user2.setSurname("Hillie");
        user2.setFleetManager(false);
        user2.setDrivingLicenseNumber(441232311);
        user2.setCreditCardNumber(12342345);
    }

    @AfterEach
    public void tearDown() {
        userService.logoutUser("1");
    }

    @Test
    void isFleetManagerTrueTest() {
        userService.register(userDto1);
        String token = userService.loginUser(loginDto1).getValue();

        // we should get the token "1" since the userId of the created user is 1
        assertThat(token).isEqualTo("1");
        assertThat(userService.isFleetManager(token)).isTrue();
    }

    @Test
    void isFleetManagerFalseTest() {
        userService.register(userDto2);
        String token = userService.loginUser(loginDto2).getValue();

        // we should get the token "1" since the userId of the created user is 1
        assertThat(token).isEqualTo("1");
        assertThat(userService.isFleetManager(token)).isFalse();
    }

    @Test
    void getAllRegularTest() {
        User registeredUser1 = userService.register(userDto1);
        User registeredUser2 = userService.register(userDto2);

        assertThat(userRepository.findAll()).isEqualTo(List.of(registeredUser1, registeredUser2));
    }

    @Test
    void getAllEmptyTest() {
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    void isLoggedInTrueTest() {
        User registeredUser = userService.register(userDto1);
        String token = userService.loginUser(loginDto1).getValue();

        assertThat(userService.isLoggedIn(token)).isTrue();
        assertThat(userService.isLoggedIn(registeredUser)).isTrue();
    }

    @Test
    void isLoggedInFalseTest() {
        String token = "1";
        assertThat(userService.isLoggedIn(token)).isFalse();
        assertThat(userService.isLoggedIn(user1)).isFalse();
    }

    @Test
    void registerTestRegular() {
        User registeredUser = userService.register(userDto1);
        assertThat(userRepository.existsById(registeredUser.getUserId())).isTrue();
    }

    @Test
    void registerTestFail() {
        User registeredUser1 = userService.register(userDto1);
        assertThat(userRepository.existsById(registeredUser1.getUserId())).isTrue();

        User registeredUser2 = userService.register(userDto1);
        assertThat(registeredUser2).isNull();
    }

    @Test
    void loginUserRegularTest() {
        userService.register(userDto1);
        Tuple<HttpStatus, String> result = userService.loginUser(loginDto1);
        String token = result.getValue();
        HttpStatus status = result.getKey();

        assertThat(token).isEqualTo("1");
        assertThat(status).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginUserFailTest() {
        Tuple<HttpStatus, String> result = userService.loginUser(loginDto1);
        String token = result.getValue();
        HttpStatus status = result.getKey();
        assertThat(token).isEqualTo(ResponseMessages.USER_NON_EXISTENT);
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void loginUserAlreadyLoggedInTest() {
        userService.register(userDto1);
        Tuple<HttpStatus, String> result1 = userService.loginUser(loginDto1);
        String token = result1.getValue();
        HttpStatus status = result1.getKey();

        assertThat(token).isEqualTo("1");
        assertThat(status).isEqualTo(HttpStatus.OK);


        Tuple<HttpStatus, String> result2 = userService.loginUser(loginDto1);
        token = result2.getValue();
        status = result2.getKey();

        assertThat(token).isEqualTo(ResponseMessages.USER_ALREADY_LOGGED_IN);
        assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void loginUserBadCredentialsTest() {

        userService.register(userDto1);
        LoginDto incorrectCredentials = new LoginDto();
        incorrectCredentials.setUsername(userDto1.getUsername());
        incorrectCredentials.setPassword("b");

        Tuple<HttpStatus, String> result = userService.loginUser(incorrectCredentials);
        String token = result.getValue();
        HttpStatus status = result.getKey();

        assertThat(token).isEqualTo(ResponseMessages.INVALID_CREDENTIALS);
        assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void logoutUserTrueTest() {
        userService.register(userDto1);

        Tuple<HttpStatus, String> resultLogin = userService.loginUser(loginDto1);
        String token = resultLogin.getValue();

        Tuple<HttpStatus, String> resultLogout = userService.logoutUser(token);
        token = resultLogout.getValue();
        HttpStatus status = resultLogout.getKey();

        assertThat(token).isEqualTo(ResponseMessages.USER_LOGGED_OUT);
        assertThat(status).isEqualTo(HttpStatus.OK);
    }

    @Test
    void logoutUserFalseTest() {
        userService.register(userDto1);
        String token = "1";

        Tuple<HttpStatus, String> resultLogout = userService.logoutUser(token);
        token = resultLogout.getValue();
        HttpStatus status = resultLogout.getKey();

        assertThat(token).isEqualTo(ResponseMessages.USER_NOT_LOGGED_IN);
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findByUsername() {
        this.userService.register(userDto1);
        assertThat(this.userRepository.findByUsername(userDto1.getUsername())).isEqualTo(user1);
    }
}
