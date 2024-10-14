package lk.ijse.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private List<OrderEntity> orders;
}
