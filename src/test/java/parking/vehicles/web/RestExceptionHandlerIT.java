package parking.vehicles.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import parking.vehicles.exception.VehicleAlreadyExists;
import parking.vehicles.exception.VehicleNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RestExceptionHandlerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleVehicleNotFoundException() throws Exception {
        mockMvc.perform(get("/vehicles/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Превозното средство не е намерено!"));
    }

//    @Test
//    void testHandleVehicleAlreadyExistsException() throws Exception {
//        String vehicleJson = "{\"licensePlate\":\"EXISTING_PLATE\", \"ownerUuid\":\"test-uuid\"}";
//
//        mockMvc.perform(post("/vehicles/user/test-uuid")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(vehicleJson))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status").value(409))
//                .andExpect(jsonPath("$.error").value("Conflict"))
//                .andExpect(jsonPath("$.message").value("Вече имате превозно средство с този регистрационен номер!"));
//    }
}
