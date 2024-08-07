package parking.vehicles.web;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import parking.vehicles.model.dto.VehicleCreateDto;
import parking.vehicles.model.dto.VehicleEditDto;
import parking.vehicles.model.dto.VehicleView;
import parking.vehicles.model.dto.VehicleViewAdmin;
import parking.vehicles.service.VehicleService;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<VehicleView> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @PostMapping("/user/{uuid}")
    public ResponseEntity<VehicleView> addVehicle(
            @RequestBody VehicleCreateDto vehicleCreateDto,
            @PathVariable String uuid) {
        VehicleView vehicleView = vehicleService.addVehicle(vehicleCreateDto, uuid);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(vehicleView.getId())
                        .toUri()
        ).body(vehicleView);
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<List<VehicleView>> viewUserVehicles(@PathVariable String uuid) {
        return ResponseEntity.ok(vehicleService.findVehiclesForUser(uuid));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleView> updateVehicle(
            @PathVariable Long id,
            @RequestBody VehicleEditDto vehicleEditDto) {
        VehicleView vehicleView = vehicleService.updateVehicle(id, vehicleEditDto);
        return ResponseEntity.ok(vehicleView);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/all-vehicles")
    public ResponseEntity<List<VehicleViewAdmin>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.findAll());
    }
}
