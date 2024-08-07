package parking.vehicles.service;

import org.springframework.stereotype.Service;
import parking.vehicles.exception.VehicleAlreadyExists;
import parking.vehicles.exception.VehicleNotFoundException;
import parking.vehicles.model.dto.VehicleCreateDto;
import parking.vehicles.model.dto.VehicleEditDto;
import parking.vehicles.model.dto.VehicleView;
import parking.vehicles.model.dto.VehicleViewAdmin;
import parking.vehicles.model.entity.Vehicle;
import parking.vehicles.repository.VehicleRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleView findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Превозното средство не е намерено!"));
        return new VehicleView(vehicle);
    }

    public VehicleView addVehicle(VehicleCreateDto vehicleCreateDto, String uuid) {
        if (vehicleRepository.existsByLicensePlateAndOwnerUuid(vehicleCreateDto.getLicensePlate(), uuid)) {
            throw new VehicleAlreadyExists("Вече имате превозно средство с този регистрационен номер!");
        }
        return new VehicleView(vehicleRepository.save(new Vehicle(vehicleCreateDto, uuid)));
    }

    public List<VehicleViewAdmin> findAll() {
        return vehicleRepository.findAll()
                .stream().map(VehicleViewAdmin::new).collect(Collectors.toList());
    }

    public List<VehicleView> findVehiclesForUser(String uuid) {
        return vehicleRepository.findAllByOwnerUuid(uuid).
                stream().map(VehicleView::new).collect(Collectors.toList());
    }


    public VehicleView updateVehicle(Long id, VehicleEditDto vehicleEditDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Превозното средство не е намерено!"));

        if (!vehicle.getLicensePlate().equals(vehicleEditDto.getLicensePlate())) {
            if (vehicleRepository.existsByLicensePlate(vehicleEditDto.getLicensePlate())) {
                throw new VehicleAlreadyExists("Вече имате превозно средство с този регистрационен номер!");
            }
        }

        vehicle.setLicensePlate(vehicleEditDto.getLicensePlate());
        vehicleRepository.save(vehicle);
        return new VehicleView(vehicle);
    }

    public boolean hasVehicles(String uuid) {
        return vehicleRepository.existsByOwnerUuid(uuid);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = this.vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Превозното средство не е намерено!"));

        vehicleRepository.delete(vehicle);
    }

}
