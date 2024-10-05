package lk.ijse.Service.impl;

import lk.ijse.Repository.CustomerRepository;
import lk.ijse.Service.CustomerService;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.CustomerEntity;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private final Mapping mapping;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        if (!customerRepository.existsById(customerDTO.getCustomerId())) {
            CustomerEntity save = customerRepository.save(mapping.convertToEntity(customerDTO));
            if (save == null && save.getCustomerId() == null) {
                throw new DataPersistFailedException("Customer save failed..!");
            }
        } else {
            throw new DataPersistFailedException("Customer already exist..!");
        }
    }

}
