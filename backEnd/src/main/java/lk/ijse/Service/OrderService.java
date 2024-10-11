package lk.ijse.Service;

import lk.ijse.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    void saveOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrders();
}
