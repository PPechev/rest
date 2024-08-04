package parking.vehicles.model.dto;

import parking.vehicles.model.entity.Vehicle;

public class VehicleViewAdmin {
  private long id;
  private String licensePlate;
  private String owner;
  
  public VehicleViewAdmin() {
  }
  
  public VehicleViewAdmin(Vehicle vehicle) {
  this.id = vehicle.getId();
  this.licensePlate = vehicle.getLicensePlate();
  this.owner = vehicle.getOwnerUuid();
  }
  
  public long getId() {
    return id;
  }
  
  public VehicleViewAdmin setId(long id) {
    this.id = id;
    return this;
  }
  
  public String getLicensePlate() {
    return licensePlate;
  }
  
  public VehicleViewAdmin setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
    return this;
  }
  
  public String getOwner() {
    return owner;
  }
  
  public VehicleViewAdmin setOwner(String owner) {
    this.owner = owner;
    return this;
  }
}
