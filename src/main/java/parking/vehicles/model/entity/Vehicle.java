package parking.vehicles.model.entity;

import jakarta.persistence.*;
import parking.vehicles.model.dto.VehicleCreateDto;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String ownerUuid;

    public Vehicle() {
    }

    public Vehicle(VehicleCreateDto vehicleCreateDto, String uuid) {
        this.licensePlate = vehicleCreateDto.getLicensePlate().toUpperCase();
        this.ownerUuid = uuid;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Vehicle setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public Vehicle setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Vehicle setId(Long id) {
        this.id = id;
        return this;
    }
}
