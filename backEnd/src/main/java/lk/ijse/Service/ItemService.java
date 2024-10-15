package lk.ijse.Service;

import lk.ijse.customObj.response.ItemResponse;
import lk.ijse.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);

    ItemResponse getItem(String itemCode);

    void deleteItem(String itemCode);

    List<ItemDTO> getAllItems();
}
