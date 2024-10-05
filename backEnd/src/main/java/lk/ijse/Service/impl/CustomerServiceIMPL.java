package lk.ijse.Service.impl;

import lk.ijse.Repository.CustomerRepository;
import lk.ijse.Service.CustomerService;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.CustomerEntity;
import lk.ijse.exception.CustomerNotFountException;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerDTO.getCustomerId());
        if (customerEntity.isPresent()) {
            customerEntity.get().setCustomerName(customerDTO.getCustomerName());
            customerEntity.get().setCustomerAddress(customerDTO.getCustomerAddress());
            customerEntity.get().setCustomerSalary(customerDTO.getCustomerSalary());
        } else {
            throw new CustomerNotFountException("Customer update failed..!");
        }
    }

    @Override
    public CustomerDTO getCustomer(String customerId) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
        if (customerEntity.isPresent()) {
            return mapping.convertToDTO(customerEntity.get());
        } else {
            throw new CustomerNotFountException("Customer not found..!");
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new CustomerNotFountException("Customer delete failed..!");
        }
    }

}
