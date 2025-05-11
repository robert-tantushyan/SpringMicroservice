package com.webrise.SpringMicroservice.dto;

import lombok.Data;

@Data
public class SubscriptionDto {
    private Long id;
    private String serviceName;
    private Double price;
    private Long userId;
}