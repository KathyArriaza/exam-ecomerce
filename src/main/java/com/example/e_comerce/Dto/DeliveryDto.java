package com.example.e_comerce.Dto;

public class DeliveryDto {
    private Long id;

    private String type;
    private String status;


    public DeliveryDto(Long id, String status, String type) {
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public DeliveryDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
