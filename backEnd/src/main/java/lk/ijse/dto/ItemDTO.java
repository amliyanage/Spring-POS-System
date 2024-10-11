package lk.ijse.dto;

import lk.ijse.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements SuperDTO {
    private String itemCode;
    private String itemName;
    private int itemQty;
    private double itemPrice;
    private List<OrderEntity> orders;
}
