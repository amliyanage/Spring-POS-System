package lk.ijse.Controller;

import lk.ijse.Service.ItemService;
import lk.ijse.customObj.response.ItemResponse;
import lk.ijse.dto.ItemDTO;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.exception.ItemNotFountException;
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
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO) {
        logger.info("Received request to save item: {}", itemDTO);

        String validationResponse = Validation.validateItem(itemDTO);
        if (validationResponse.equals("Invalid")) {
            logger.warn("Validation failed for item: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            itemService.saveItem(itemDTO);
            logger.info("Item successfully saved: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save item due to persistence issue: {}", itemDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error occurred while saving item: {}", itemDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@RequestBody ItemDTO itemDTO) {
        logger.info("Received request to update item: {}", itemDTO);

        String validationResponse = Validation.validateItem(itemDTO);
        if (validationResponse.equals("Invalid")) {
            logger.warn("Validation failed for item: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            itemService.updateItem(itemDTO);
            logger.info("Item successfully updated: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ItemNotFountException e) {
            logger.error("Item not found for update: {}", itemDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error occurred while updating item: {}", itemDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{itemCode}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable("itemCode") String itemCode) {
        logger.info("Received request to fetch item with itemCode: {}", itemCode);

        ItemResponse itemResponse = itemService.getItem(itemCode);
        logger.info("Item fetched successfully for itemCode: {}", itemCode);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{itemCode}")
    public ResponseEntity<Void> deleteItem(@PathVariable("itemCode") String itemCode) {
        logger.info("Received request to delete item with itemCode: {}", itemCode);

        try {
            itemService.deleteItem(itemCode);
            logger.info("Item successfully deleted for itemCode: {}", itemCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ItemNotFountException e) {
            logger.error("Item not found for deletion: {}", itemCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error occurred while deleting item: {}", itemCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        logger.info("Received request to fetch all items");

        try {
            List<ItemDTO> allItems = itemService.getAllItems();
            logger.info("All items fetched successfully");
            return new ResponseEntity<>(allItems, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal server error occurred while fetching all items", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
