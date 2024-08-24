package com.example.e_comerce.controllers;

import com.example.e_comerce.Dto.CustomerDto;
import com.example.e_comerce.Dto.DeliveryDto;
import com.example.e_comerce.Dto.OrderDto;
import com.example.e_comerce.entities.Customer;
import com.example.e_comerce.entities.Delivery;
import com.example.e_comerce.entities.Order;
import com.example.e_comerce.repositories.CustomerRepository;
import com.example.e_comerce.repositories.DeliveryRepository;
import com.example.e_comerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return orders.stream().map(order -> {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setOrderDate(order.getOrderDate());

            if (order.getCustomer() != null) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(order.getCustomer().getId());
                customerDto.setName(order.getCustomer().getName());
                customerDto.setEmail(order.getCustomer().getEmail());
                customerDto.setAddress(order.getCustomer().getAddress());
                orderDto.setCustomer(customerDto);
            }

            if (order.getDelivery() != null) {
                DeliveryDto deliveryDto = new DeliveryDto();
                deliveryDto.setId(order.getDelivery().getId());
                deliveryDto.setType(order.getDelivery().getType());
                deliveryDto.setStatus(order.getDelivery().getStatus());
                orderDto.setDelivery(deliveryDto);
            }

            return orderDto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.get().getId());
            orderDto.setOrderDate(order.get().getOrderDate());

            if (order.get().getCustomer() != null) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(order.get().getCustomer().getId());
                customerDto.setName(order.get().getCustomer().getName());
                customerDto.setEmail(order.get().getCustomer().getEmail());
                customerDto.setAddress(order.get().getCustomer().getAddress());
                orderDto.setCustomer(customerDto);
            }

            if (order.get().getDelivery() != null) {
                DeliveryDto deliveryDto = new DeliveryDto();
                deliveryDto.setId(order.get().getDelivery().getId());
                deliveryDto.setType(order.get().getDelivery().getType());
                deliveryDto.setStatus(order.get().getDelivery().getStatus());
                orderDto.setDelivery(deliveryDto);
            }

            return ResponseEntity.ok(orderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        // Validación de entrada
        if (orderDto == null) {
            return ResponseEntity.badRequest().body("OrderDto cannot be null.");
        }

        // Creación de la entidad Order
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());

        // Asociar Customer
        if (orderDto.getCustomer() != null && orderDto.getCustomer().getId() != null) {
            Optional<Customer> customerOpt = customerRepository.findById(orderDto.getCustomer().getId());
            if (customerOpt.isPresent()) {
                order.setCustomer(customerOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Customer with ID " + orderDto.getCustomer().getId() + " not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("Customer ID is missing.");
        }

        // Asociar Delivery
        if (orderDto.getDelivery() != null && orderDto.getDelivery().getId() != null) {
            Optional<Delivery> deliveryOpt = deliveryRepository.findById(orderDto.getDelivery().getId());
            if (deliveryOpt.isPresent()) {
                order.setDelivery(deliveryOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Delivery with ID " + orderDto.getDelivery().getId() + " not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("Delivery ID is missing.");
        }

        // Guardar la nueva orden en la base de datos
        Order newOrder = orderRepository.save(order);

        // Crear el DTO para la respuesta
        OrderDto createdOrderDto = new OrderDto();
        createdOrderDto.setId(newOrder.getId());
        createdOrderDto.setOrderDate(newOrder.getOrderDate());

        if (newOrder.getCustomer() != null) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(newOrder.getCustomer().getId());
            customerDto.setName(newOrder.getCustomer().getName());
            customerDto.setEmail(newOrder.getCustomer().getEmail());
            customerDto.setAddress(newOrder.getCustomer().getAddress());
            createdOrderDto.setCustomer(customerDto);
        }

        if (newOrder.getDelivery() != null) {
            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setId(newOrder.getDelivery().getId());
            deliveryDto.setType(newOrder.getDelivery().getType());
            deliveryDto.setStatus(newOrder.getDelivery().getStatus());
            createdOrderDto.setDelivery(deliveryDto);
        }

        return ResponseEntity.status(201).body("Order created successfully with ID: " + createdOrderDto.getId());
    }





    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);
        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();
            existingOrder.setOrderDate(orderDto.getOrderDate());

            if (orderDto.getCustomer() != null) {
                Optional<Customer> customer = customerRepository.findById(orderDto.getCustomer().getId());
                customer.ifPresent(existingOrder::setCustomer);
            }

            if (orderDto.getDelivery() != null) {
                Optional<Delivery> delivery = deliveryRepository.findById(orderDto.getDelivery().getId());
                delivery.ifPresent(existingOrder::setDelivery);
            }

            Order updatedOrder = orderRepository.save(existingOrder);

            OrderDto updatedOrderDto = new OrderDto();
            updatedOrderDto.setId(updatedOrder.getId());
            updatedOrderDto.setOrderDate(updatedOrder.getOrderDate());

            if (updatedOrder.getCustomer() != null) {
                CustomerDto customerDto = new CustomerDto();
                customerDto.setId(updatedOrder.getCustomer().getId());
                customerDto.setName(updatedOrder.getCustomer().getName());
                customerDto.setEmail(updatedOrder.getCustomer().getEmail());
                customerDto.setAddress(updatedOrder.getCustomer().getAddress());
                updatedOrderDto.setCustomer(customerDto);
            }

            if (updatedOrder.getDelivery() != null) {
                DeliveryDto deliveryDto = new DeliveryDto();
                deliveryDto.setId(updatedOrder.getDelivery().getId());
                deliveryDto.setType(updatedOrder.getDelivery().getType());
                deliveryDto.setStatus(updatedOrder.getDelivery().getStatus());
                updatedOrderDto.setDelivery(deliveryDto);
            }

            return ResponseEntity.ok(updatedOrderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
