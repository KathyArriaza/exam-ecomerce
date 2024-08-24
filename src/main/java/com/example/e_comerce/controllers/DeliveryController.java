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
@RequestMapping("/api/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    // Obtener todas las entregas
    @GetMapping
    public List<DeliveryDto> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener una entrega por ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            return ResponseEntity.ok(convertToDto(delivery.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva entrega
    @PostMapping
    public DeliveryDto createDelivery(@RequestBody DeliveryDto deliveryDto) {
        Delivery delivery = convertToEntity(deliveryDto);
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return convertToDto(savedDelivery);
    }

    // Actualizar una entrega existente
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDto> updateDelivery(@PathVariable Long id, @RequestBody DeliveryDto deliveryDto) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            Delivery updatedDelivery = delivery.get();
            updatedDelivery.setType(deliveryDto.getType());
            updatedDelivery.setStatus(deliveryDto.getStatus());
            Delivery savedDelivery = deliveryRepository.save(updatedDelivery);
            return ResponseEntity.ok(convertToDto(savedDelivery));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una entrega
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            deliveryRepository.delete(delivery.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de conversión entre Delivery y DeliveryDto
    private DeliveryDto convertToDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(delivery.getId());           // Asignar el ID
        deliveryDto.setType(delivery.getType());       // Asignar el tipo
        deliveryDto.setStatus(delivery.getStatus());   // Asignar el estado
        return deliveryDto;
    }


    private Delivery convertToEntity(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setType(deliveryDto.getType());       // Asignar el tipo desde el DTO
        delivery.setStatus(deliveryDto.getStatus());   // Asignar el estado desde el DTO
        // El ID no se asigna aquí ya que se genera automáticamente en el caso de una nueva entidad
        return delivery;
    }
}




