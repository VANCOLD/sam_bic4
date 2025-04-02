package fh.technikum.sam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fh.technikum.sam.models.User;
import fh.technikum.sam.models.dto.LoginDto;
import fh.technikum.sam.models.dto.UserDto;
import fh.technikum.sam.services.DtoTransformerService;
import fh.technikum.sam.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static fh.technikum.sam.utils.ResponseMessages.USER_ALREADY_EXISTS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private DtoTransformerService dtoTransformerService;

    @InjectMocks
    private UserController userController;


    private static final String shortenedToken = "1";
    private static final UserDto userDto1 = new UserDto();
    private static final UserDto userDto2 = new UserDto();
    private static final User user1 = new User();
    private static final User user2 = new User();
    private static final LoginDto loginDto1 = new LoginDto();
    private static final LoginDto loginDto2 = new LoginDto();

    @BeforeAll
    public static void initializeData() {
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
        user1.setPassword("test1234");
        user1.setAge(21);
        user1.setFirstName("Patrick");
        user1.setSurname("Sommer");
        user1.setFleetManager(true);
        user1.setDrivingLicenseNumber(1234);
        user1.setCreditCardNumber(4321);

        user2.setUserId(2L);
        user2.setUsername("test");
        user2.setPassword("abcdefg");
        user2.setAge(74);
        user2.setFirstName("Herbert");
        user2.setSurname("Hillie");
        user2.setFleetManager(false);
        user2.setDrivingLicenseNumber(441232311);
        user2.setCreditCardNumber(12342345);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void registerRegularTest() throws Exception {

        when(userService.register(userDto1)).thenReturn(user1);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(user1)));

        verify(userService).register(userDto1);
    }


    @Test
    void registerNullTest() throws Exception {
        when(userService.register(userDto1)).thenReturn(null);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(USER_ALREADY_EXISTS));

        verify(userService).register(userDto1);
    }


    @Test
    void getAllRegularTest() throws Exception {

        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(userService.getAll()).thenReturn(List.of(user1, user2));
        when(dtoTransformerService.transformToDto(user1, UserDto.class)).thenReturn(userDto1);
        when(dtoTransformerService.transformToDto(user2, UserDto.class)).thenReturn(userDto2);

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(userDto1, userDto2))));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(userService).getAll();
        verify(dtoTransformerService).transformToDto(user1, UserDto.class);
        verify(dtoTransformerService).transformToDto(user2, UserDto.class);
    }


    @Test
    void getAllRegularEmptyTest() throws Exception {

        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(userService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isNoContent());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(userService).getAll();
    }


    @Test
    void getAllInvalidTokenNoFleetManagerTest() throws Exception {
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());


        verify(userService, never()).isLoggedIn(anyString());
        verify(userService).isFleetManager(shortenedToken);
    }


    @Test
    void getAllInvalidTokenTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(false);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isFleetManager(shortenedToken);
        verify(userService).isLoggedIn(shortenedToken);
    }


    @Test
    void getAllNotFleetManagerTest() throws Exception {
        // due to short-circuit evaluation isLoggedIn won't be checked and cant be verified!
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isFleetManager(shortenedToken);
        verify(userService, never()).isLoggedIn(anyString());
    }

}