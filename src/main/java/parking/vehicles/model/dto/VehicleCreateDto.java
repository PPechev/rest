package parking.vehicles.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VehicleCreateDto {
  @NotBlank
  @Pattern(regexp = "^([A-Za-z]{1,2}\\d{4}[A-Za-z]{1,2})|([A-Za-z]{1,2}\\d{4})|([A-Za-z]{2}\\d{3}[A-Za-z]{1})$",
      message = "Невалиден формат на регистрационен номер!")
  private String licensePlate;
  
  public VehicleCreateDto() {
  }
  
  public String getLicensePlate() {
    return licensePlate;
  }
  
  public VehicleCreateDto setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
    return this;
  }
}
