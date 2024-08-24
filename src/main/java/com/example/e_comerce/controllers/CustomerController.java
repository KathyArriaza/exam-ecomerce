package com.example.e_comerce.controllers;

import com.example.e_comerce.Dto.CustomerDto;
import com.example.e_comerce.entities.Customer;
import com.example.e_comerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            CustomerDto customerDto = convertToDto(customerOpt.get());
            return ResponseEntity.ok(customerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = convertToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto savedCustomerDto = convertToDto(savedCustomer);
        return ResponseEntity.status(201).body(savedCustomerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer existingCustomer = customerOpt.get();
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setAddress(customerDto.getAddress());
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            CustomerDto updatedCustomerDto = convertToDto(updatedCustomer);
            return ResponseEntity.ok(updatedCustomerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            customerRepository.delete(customerOpt.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de conversión
    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress());
    }

    private Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        return customer;
    }
}


