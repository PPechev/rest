package parking.vehicles.web;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import parking.vehicles.exception.VehicleAlreadyExists;
import parking.vehicles.model.dto.VehicleCreateDto;
import parking.vehicles.model.dto.VehicleEditDto;
import parking.vehicles.model.dto.VehicleView;
import parking.vehicles.model.dto.VehicleViewAdmin;
import parking.vehicles.service.VehicleService;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerIT {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private VehicleView vehicleView;
    private VehicleCreateDto vehicleCreateDto;
    private VehicleEditDto vehicleEditDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController)
                .setControllerAdvice(new RestExceptionHandler())  // Добавете контролер съветник за обработка на изключения
                .build();

        vehicleView = new VehicleView();
        vehicleView.setId(1L);
        vehicleView.setLicensePlate("123ABC");

        vehicleCreateDto = new VehicleCreateDto();
        vehicleCreateDto.setLicensePlate("123ABC");

        vehicleEditDto = new VehicleEditDto();
        vehicleEditDto.setLicensePlate("456DEF");
    }

    @Test
    void testGetVehicleById() throws Exception {
        when(vehicleService.findById(anyLong())).thenReturn(vehicleView);

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.licensePlate").value("123ABC"));

        verify(vehicleService, times(1)).findById(anyLong());
    }

    @Test
    void testAddVehicle() throws Exception {
        when(vehicleService.addVehicle(any(VehicleCreateDto.class), anyString())).thenThrow(new VehicleAlreadyExists("Вече имате превозно средство с този регистрационен номер!"));

        mockMvc.perform(post("/vehicles/user/{uuid}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\":\"EXISTING_PLATE\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Вече имате превозно средство с този регистрационен номер!"));

        verify(vehicleService, times(1)).addVehicle(any(VehicleCreateDto.class), anyString());
    }

    @Test
    void testViewUserVehicles() throws Exception {
        when(vehicleService.findVehiclesForUser(anyString())).thenReturn(List.of(vehicleView));

        mockMvc.perform(get("/vehicles/user/{uuid}", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].licensePlate").value("123ABC"));

        verify(vehicleService, times(1)).findVehiclesForUser(anyString());
    }

    @Test
    void testUpdateVehicle() throws Exception {
        when(vehicleService.updateVehicle(anyLong(), any(VehicleEditDto.class))).thenReturn(vehicleView);

        mockMvc.perform(put("/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\":\"456DEF\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.licensePlate").value("123ABC"));

        verify(vehicleService, times(1)).updateVehicle(anyLong(), any(VehicleEditDto.class));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(anyLong());

        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicle(anyLong());
    }

    @Test
    void testGetAllVehicles() throws Exception {
        when(vehicleService.findAll()).thenReturn(List.of(new VehicleViewAdmin()));

        mockMvc.perform(get("/vehicles/admin/all-vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(vehicleService, times(1)).findAll();
    }
}
