package parking.vehicles.model.dto;

import parking.vehicles.model.entity.Vehicle;

public class VehicleView {
  private long id;
  private String licensePlate;
  
  public VehicleView() {
  }
  
  public VehicleView(Vehicle vehicle) {
    this.id = vehicle.getId();
    this.licensePlate = vehicle.getLicensePlate();
  }
  
  public long getId() {
    return id;
  }
  
  public VehicleView setId(long id) {
    this.id = id;
    return this;
  }
  
  public String getLicensePlate() {
    return licensePlate;
  }
  
  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }
}
