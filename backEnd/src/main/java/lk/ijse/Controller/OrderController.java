package lk.ijse.Controller;

import lk.ijse.Service.OrderService;
import lk.ijse.dto.OrderDTO;
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
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Received request to save order: {}", orderDTO);

        String validationResponse = Validation.OrderValidation(orderDTO);
        if (validationResponse.equals("Invalid")) {
            logger.warn("Validation failed for order: {}", orderDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            orderService.saveOrder(orderDTO);
            logger.info("Order successfully saved: {}", orderDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save order due to persistence issue: {}", orderDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error occurred while saving order: {}", orderDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        logger.info("Received request to fetch all orders");

        try {
            List<OrderDTO> allOrders = orderService.getAllOrders();
            logger.info("All orders fetched successfully");
            return new ResponseEntity<>(allOrders, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal server error occurred while fetching orders", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
