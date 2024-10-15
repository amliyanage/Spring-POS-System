package lk.ijse.Controller;

import lk.ijse.Service.CustomerService;
import lk.ijse.customObj.response.CustomerResponse;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.exception.CustomerNotFountException;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){

        String validationResponse = Validation.validateCustomer(customerDTO);
        if (validationResponse.equals("Invalid")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            customerService.saveCustomer(customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDTO customerDTO){
        String validationResponse = Validation.validateCustomer(customerDTO);
        if (validationResponse.equals("Invalid")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            customerService.updateCustomer(customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomerNotFountException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("customerId") String customerId){
        return new ResponseEntity<>(customerService.getCustomer(customerId),HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId){
        try {
            customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerNotFountException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try {
            List<CustomerDTO> allCustomers = customerService.getAllCustomers();
            return new ResponseEntity<>(allCustomers,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
