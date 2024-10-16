package lk.ijse.Controller;

import lk.ijse.Service.CustomerService;
import lk.ijse.customObj.response.CustomerResponse;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.exception.CustomerNotFountException;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Validation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){

        String validationResponse = Validation.validateCustomer(customerDTO);
        if (validationResponse.equals("Invalid")){
            logger.warn("Invalid customer data: {}", customerDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            customerService.saveCustomer(customerDTO);
            logger.info("Customer saved successfully: {}", customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Internal server error while saving customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDTO customerDTO){
        String validationResponse = Validation.validateCustomer(customerDTO);
        if (validationResponse.equals("Invalid")){
            logger.warn("Invalid customer data for update: {}", customerDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            customerService.updateCustomer(customerDTO);
            logger.info("Customer updated successfully: {}", customerDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CustomerNotFountException e) {
            logger.error("Customer not found for update: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Internal server error while updating customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("customerId") String customerId){
        logger.info("Fetching customer with ID: {}", customerId);
        return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") String customerId){
        try {
            customerService.deleteCustomer(customerId);
            logger.info("Customer deleted successfully with ID: {}", customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerNotFountException e) {
            logger.error("Customer not found for deletion: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            logger.error("Internal server error while deleting customer: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        try {
            List<CustomerDTO> allCustomers = customerService.getAllCustomers();
            logger.info("Fetching all customers, total count: {}", allCustomers.size());
            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Internal server error while fetching all customers: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
