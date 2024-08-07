package parking.vehicles.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VehicleEditDto {
    private Long id;
    @NotBlank
    @Pattern(regexp = "^([A-Za-z]{1,2}\\d{4}[A-Za-z]{1,2})|([A-Za-z]{1,2}\\d{4})|([A-Za-z]{2}\\d{3}[A-Za-z]{1})$",
            message = "Невалиден формат на регистрационен номер!")
    private String licensePlate;

    public VehicleEditDto() {
    }

    public Long getId() {
        return id;
    }

    public VehicleEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleEditDto setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }
}
