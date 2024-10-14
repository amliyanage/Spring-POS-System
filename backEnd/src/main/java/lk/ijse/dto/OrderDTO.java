package lk.ijse.dto;

import lk.ijse.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO implements SuperDTO {
    private String orderId;
    private double balance;
    private String date;
    private double discount;
    private double total;
    private double subTotal;
    // Instead of embedding the entire CustomerDTO, just use customerId
    private CustomerDTO customer;
    private List<ItemDTO> items;
}

