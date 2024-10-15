package lk.ijse.Controller;

import lk.ijse.Service.ItemService;
import lk.ijse.customObj.response.ItemResponse;
import lk.ijse.dto.ItemDTO;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.exception.ItemNotFountException;
import lk.ijse.util.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    @Autowired
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO){

        String validationResponse = Validation.validateItem(itemDTO);

        if (validationResponse.equals("Invalid")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@RequestBody ItemDTO itemDTO){

        String validationResponse = Validation.validateItem(itemDTO);

        if (validationResponse.equals("Invalid")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            itemService.updateItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ItemNotFountException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{itemCode}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("itemCode") String itemCode){
        return new ResponseEntity<>(itemService.getItem(itemCode),HttpStatus.OK);
    }

    @DeleteMapping("/{itemCode}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemCode") String itemCode){
        try {
            itemService.deleteItem(itemCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ItemNotFountException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ItemDTO>> getAllItems(){
        try {
            List<ItemDTO> allItems = itemService.getAllItems();
            return new ResponseEntity<>(allItems,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
