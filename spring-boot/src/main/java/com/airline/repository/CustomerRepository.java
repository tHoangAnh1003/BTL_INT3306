package com.airline.repository;

import java.util.List;
import com.airline.repository.entity.CustomerEntity;

public interface CustomerRepository {
    List<CustomerEntity> findAll();
    CustomerEntity findById(Long id);
    void save(CustomerEntity customer);
    void update(CustomerEntity customer);
    void delete(Long id);
    CustomerEntity findByEmail(String email);
}
