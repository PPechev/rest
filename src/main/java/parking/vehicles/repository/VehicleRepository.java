package parking.vehicles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parking.vehicles.model.entity.Vehicle;


import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
  boolean existsByLicensePlateAndOwnerUuid(String licensePlate, String ownerUuid);
  
  List<Vehicle> findAllByOwnerUuid(String uuid);
  
  boolean existsByLicensePlate(String licensePlate);
  
  boolean existsByOwnerUuid(String uuid);
}