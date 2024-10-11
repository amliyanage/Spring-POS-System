package lk.ijse.Controller;

import lk.ijse.Service.ItemService;
import lk.ijse.Service.OrderService;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.exception.ItemNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.saveOrder(orderDTO);
            orderDTO.getItems().forEach(itemDTO -> {
                ItemDTO item = itemService.getItem(itemDTO.getItemCode());
                item.setItemQty(item.getItemQty() - itemDTO.getItemQty());
                itemService.updateItem(item);
            });
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
