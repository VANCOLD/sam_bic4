package fh.technikum.sam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fh.technikum.sam.models.Vehicle;
import fh.technikum.sam.models.dto.VehicleDto;
import fh.technikum.sam.models.enums.Priority;
import fh.technikum.sam.services.DtoTransformerService;
import fh.technikum.sam.services.UserService;
import fh.technikum.sam.services.VehicleService;
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

import static fh.technikum.sam.utils.ResponseMessages.NO_VEHICLE_FOUND;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private DtoTransformerService dtoTransformerService;

    @InjectMocks
    private VehicleController vehicleController;

    private static final String shortenedToken = "1";
    private static final Vehicle vehicle1 = new Vehicle();
    private static final Vehicle vehicle2 = new Vehicle();
    private static final VehicleDto vehicleDto1 = new VehicleDto();
    private static final VehicleDto vehicleDto2 = new VehicleDto();

    @BeforeAll
    public static void initializeData() {

        vehicle1.setTimeSinceLastUpdate(20.0);
        vehicle1.setPriority(Priority.CRITICAL);
        vehicle1.setDistanceSinceLastUpdate(40.0);
        vehicle1.setDriverId(1);
        vehicle1.setEmergencyDescription("");
        vehicle1.setIsOccupied(true);
        vehicle1.setLatitude(10.5);
        vehicle1.setLongitude(5.5);
        vehicle1.setVehicleId(1L);

        vehicle2.setTimeSinceLastUpdate(1333.0);
        vehicle2.setPriority(Priority.MEDIUM);
        vehicle2.setDistanceSinceLastUpdate(10.0);
        vehicle2.setEmergencyDescription("Jo jo");
        vehicle2.setIsOccupied(false);
        vehicle2.setLatitude(50.0);
        vehicle2.setLongitude(35.35);
        vehicle2.setVehicleId(2L);

        vehicleDto1.setTimeSinceLastUpdate(20.0);
        vehicleDto1.setPriority(Priority.CRITICAL);
        vehicleDto1.setDistanceSinceLastUpdate(40.0);
        vehicleDto1.setDriverId(1);
        vehicleDto1.setEmergencyDescription("");
        vehicleDto1.setIsOccupied(true);
        vehicleDto1.setLatitude(10.5);
        vehicleDto1.setLongitude(5.5);
        vehicleDto1.setVehicleId(1L);

        vehicleDto2.setTimeSinceLastUpdate(1333.0);
        vehicleDto2.setPriority(Priority.MEDIUM);
        vehicleDto2.setDistanceSinceLastUpdate(10.0);
        vehicleDto2.setEmergencyDescription("Jo jo");
        vehicleDto2.setIsOccupied(false);
        vehicleDto2.setLatitude(50.0);
        vehicleDto2.setLongitude(35.35);
        vehicleDto2.setVehicleId(2L);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
        objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule()); // used for timestamp transformation inside test, works in regular prod environment
    }


    @Test
    void getAllInvalidTokenTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/vehicles")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService, never()).isFleetManager(anyString());
    }


    @Test
    void getByIdInvalidTokenTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService, never()).isFleetManager(anyString());
    }


    @Test
    void createInvalidTokenTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(false);
        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto1))
                        .header("Authorization", "Bearer 1"))
                        .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService, never()).isFleetManager(anyString());
    }


    @Test
    void deleteInvalidTokenTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(false);
        mockMvc.perform(delete("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService, never()).isFleetManager(anyString());
    }


    @Test
    void getAllNotFleetManagerTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/vehicles")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
    }


    @Test
    void getByIdNotFleetManagerTest() throws Exception{
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(get("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
    }


    @Test
    void createNotFleetManagerTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto1))
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
    }


    @Test
    void deleteNotFleetManagerTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(false);
        mockMvc.perform(delete("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isForbidden());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
    }


    @Test
    void getAllRegularTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.getAll()).thenReturn(List.of(vehicle1, vehicle2));
        when(dtoTransformerService.transformToDto(vehicle1, VehicleDto.class)).thenReturn(vehicleDto1);
        when(dtoTransformerService.transformToDto(vehicle2, VehicleDto.class)).thenReturn(vehicleDto2);

        mockMvc.perform(get("/api/vehicles")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(vehicleDto1, vehicleDto2))));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).getAll();
        verify(dtoTransformerService).transformToDto(vehicle1, VehicleDto.class);
        verify(dtoTransformerService).transformToDto(vehicle1, VehicleDto.class);
    }

    @Test
    void getAllEmptyTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/vehicles")
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isNoContent());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).getAll();
        verify(dtoTransformerService, never()).transformToDto(any(),any());
    }

    @Test
    void getByIdRegularTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.getById(vehicle1.getVehicleId())).thenReturn(vehicle1);
        when(dtoTransformerService.transformToDto(vehicle1, VehicleDto.class)).thenReturn(vehicleDto1);

        mockMvc.perform(get("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleDto1)));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).getById(vehicle1.getVehicleId());
        verify(dtoTransformerService).transformToDto(vehicle1, VehicleDto.class);
    }

    @Test
    void getByIdNoVehicleFoundTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.getById(vehicle1.getVehicleId())).thenReturn(null);

        mockMvc.perform(get("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(NO_VEHICLE_FOUND + vehicle1.getVehicleId()));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).getById(vehicle1.getVehicleId());
        verify(dtoTransformerService, never()).transformToDto(any(), any());
    }

    @Test
    void deleteByIdRegularTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.deleteById(vehicle1.getVehicleId())).thenReturn(vehicle1);
        when(dtoTransformerService.transformToDto(vehicle1, VehicleDto.class)).thenReturn(vehicleDto1);

        mockMvc.perform(delete("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleDto1)));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).deleteById(vehicle1.getVehicleId());
        verify(dtoTransformerService).transformToDto(vehicle1, VehicleDto.class);
    }

    @Test
    void deleteByIdNoVehicleFoundTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.deleteById(vehicle1.getVehicleId())).thenReturn(null);

        mockMvc.perform(delete("/api/vehicles/{vehicleId}", vehicle1.getVehicleId())
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isNoContent());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).deleteById(vehicle1.getVehicleId());
        verify(dtoTransformerService, never()).transformToDto(any(), any());
    }

    @Test
    void createRegularTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.create(vehicleDto1)).thenReturn(vehicle1);
        when(dtoTransformerService.transformToDto(vehicle1, VehicleDto.class)).thenReturn(vehicleDto1);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto1))
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleDto1)));

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).create(vehicleDto1);
        verify(dtoTransformerService).transformToDto(vehicle1, VehicleDto.class);
    }

    @Test
    void createFailTest() throws Exception {
        when(userService.isLoggedIn(shortenedToken)).thenReturn(true);
        when(userService.isFleetManager(shortenedToken)).thenReturn(true);
        when(vehicleService.create(vehicleDto1)).thenReturn(null);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDto1))
                        .header("Authorization", "Bearer 1"))
                .andExpect(status().isBadRequest());

        verify(userService).isLoggedIn(shortenedToken);
        verify(userService).isFleetManager(shortenedToken);
        verify(vehicleService).create(vehicleDto1);
        verify(dtoTransformerService, never()).transformToDto(any(), any());
    }
}