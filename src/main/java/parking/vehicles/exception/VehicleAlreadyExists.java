package parking.vehicles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//todo change response status
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VehicleAlreadyExists extends RuntimeException{
  public VehicleAlreadyExists(String message) {
    super(message);
  }
}