package lk.ijse.Service.impl;

import lk.ijse.Repository.OrderRepository;
import lk.ijse.Service.OrderService;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.OrderEntity;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceIMPL implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        if (!orderRepository.existsById(orderDTO.getOrderId())){
            orderRepository.save(mapping.convertToEntity(orderDTO));
        }else {
            throw new DataPersistFailedException("Order already exist..!");
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> all = orderRepository.findAll();
        return mapping.convertOrderToDTOList(all);
    }

}
