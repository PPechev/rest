package parking.vehicles.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import parking.vehicles.exception.VehicleAlreadyExists;
import parking.vehicles.exception.VehicleNotFoundException;
import parking.vehicles.model.dto.VehicleCreateDto;
import parking.vehicles.model.dto.VehicleEditDto;
import parking.vehicles.model.dto.VehicleView;
import parking.vehicles.model.dto.VehicleViewAdmin;
import parking.vehicles.model.entity.Vehicle;
import parking.vehicles.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private String ownerUuid;
    private VehicleCreateDto vehicleCreateDto;
    private VehicleEditDto vehicleEditDto;

    @BeforeEach
    void setUp() {
        ownerUuid = UUID.randomUUID().toString();
        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("123ABC");
        vehicle.setOwnerUuid(ownerUuid);

        vehicleCreateDto = new VehicleCreateDto();
        vehicleCreateDto.setLicensePlate("123ABC");

        vehicleEditDto = new VehicleEditDto();
        vehicleEditDto.setLicensePlate("456DEF");
    }

    @Test
    void testFindById_VehicleExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        VehicleView vehicleView = vehicleService.findById(1L);

        assertNotNull(vehicleView);
        assertEquals(vehicle.getLicensePlate(), vehicleView.getLicensePlate());
    }

    @Test
    void testFindById_VehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.findById(1L));
    }

    @Test
    void testAddVehicle_VehicleDoesNotExist() {
        when(vehicleRepository.existsByLicensePlateAndOwnerUuid(vehicleCreateDto.getLicensePlate(), ownerUuid)).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleView vehicleView = vehicleService.addVehicle(vehicleCreateDto, ownerUuid);

        assertNotNull(vehicleView);
        assertEquals(vehicleCreateDto.getLicensePlate(), vehicleView.getLicensePlate());
    }

    @Test
    void testAddVehicle_VehicleAlreadyExists() {
        when(vehicleRepository.existsByLicensePlateAndOwnerUuid(vehicleCreateDto.getLicensePlate(), ownerUuid)).thenReturn(true);

        assertThrows(VehicleAlreadyExists.class, () -> vehicleService.addVehicle(vehicleCreateDto, ownerUuid));
    }

    @Test
    void testFindAll() {
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));

        List<VehicleViewAdmin> vehicles = vehicleService.findAll();

        assertEquals(1, vehicles.size());
        assertEquals(vehicle.getLicensePlate(), vehicles.get(0).getLicensePlate());
    }

    @Test
    void testFindVehiclesForUser() {
        when(vehicleRepository.findAllByOwnerUuid(ownerUuid)).thenReturn(List.of(vehicle));

        List<VehicleView> vehicles = vehicleService.findVehiclesForUser(ownerUuid);

        assertEquals(1, vehicles.size());
        assertEquals(vehicle.getLicensePlate(), vehicles.get(0).getLicensePlate());
    }

    @Test
    void testUpdateVehicle_VehicleExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.existsByLicensePlate(vehicleEditDto.getLicensePlate())).thenReturn(false);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        VehicleView vehicleView = vehicleService.updateVehicle(1L, vehicleEditDto);

        assertNotNull(vehicleView);
        assertEquals(vehicleEditDto.getLicensePlate(), vehicleView.getLicensePlate());
    }

    @Test
    void testUpdateVehicle_VehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.updateVehicle(1L, vehicleEditDto));
    }

    @Test
    void testUpdateVehicle_VehicleAlreadyExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.existsByLicensePlate(vehicleEditDto.getLicensePlate())).thenReturn(true);

        assertThrows(VehicleAlreadyExists.class, () -> vehicleService.updateVehicle(1L, vehicleEditDto));
    }

    @Test
    void testHasVehicles_UserHasVehicles() {
        when(vehicleRepository.existsByOwnerUuid(ownerUuid)).thenReturn(true);

        assertTrue(vehicleService.hasVehicles(ownerUuid));
    }

    @Test
    void testHasVehicles_UserHasNoVehicles() {
        when(vehicleRepository.existsByOwnerUuid(ownerUuid)).thenReturn(false);

        assertFalse(vehicleService.hasVehicles(ownerUuid));
    }

    @Test
    void testDeleteVehicle_VehicleExists() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        doNothing().when(vehicleRepository).delete(vehicle);

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository, times(1)).delete(vehicle);
    }

    @Test
    void testDeleteVehicle_VehicleNotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.deleteVehicle(1L));
    }
}
