package lk.ijse.Controller;

import lk.ijse.Service.ItemService;
import lk.ijse.dto.ItemDTO;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.exception.ItemNotFountException;
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
    public ResponseEntity<String> saveItem(@RequestBody ItemDTO itemDTO){
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
    public ResponseEntity<String> updateItem(@RequestBody ItemDTO itemDTO){
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
    public ResponseEntity<ItemDTO> getItem(@PathVariable("itemCode") String itemCode){
        try {
            ItemDTO item = itemService.getItem(itemCode);
            return new ResponseEntity<>(item,HttpStatus.OK);
        } catch (ItemNotFountException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{itemCode}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemCode") String itemCode){
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
