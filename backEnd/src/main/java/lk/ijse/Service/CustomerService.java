package lk.ijse.Service;

import lk.ijse.dto.CustomerDTO;
import org.springframework.stereotype.Service;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);

    void updateCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(String customerId);

    void deleteCustomer(String customerId);
}
