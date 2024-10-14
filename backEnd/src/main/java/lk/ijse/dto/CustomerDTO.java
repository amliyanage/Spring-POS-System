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
public class CustomerDTO implements SuperDTO {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private double customerSalary;

    @JsonIgnore
    private List<OrderDTO> orders;
}
