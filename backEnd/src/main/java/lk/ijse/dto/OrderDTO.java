package lk.ijse.dto;

import lk.ijse.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements SuperDTO{
    private String orderId;
    private double balance;
    private String date;
    private double discount;
    private double total;
    private double subTotal;
    private CustomerDTO customerDTO;
    private List<ItemDTO> items;
}
