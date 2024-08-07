package parking.vehicles.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import parking.vehicles.exception.VehicleAlreadyExists;
import parking.vehicles.exception.VehicleNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {
  
  @ExceptionHandler(VehicleNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleVehicleNotFoundException(VehicleNotFoundException ex) {
    return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
  }
  
  @ExceptionHandler(VehicleAlreadyExists.class)
  public ResponseEntity<Map<String, Object>> handleVehicleAlreadyExistsException(VehicleAlreadyExists ex) {
    return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
  }
  
  
  private ResponseEntity<Map<String, Object>> buildResponseEntity(HttpStatus status, String message) {
    Map<String, Object> errorAttributes = new HashMap<>();
    errorAttributes.put("timestamp", LocalDateTime.now());
    errorAttributes.put("status", status.value());
    errorAttributes.put("error", status.getReasonPhrase());
    errorAttributes.put("message", message);
    return new ResponseEntity<>(errorAttributes, status);
  }
}
