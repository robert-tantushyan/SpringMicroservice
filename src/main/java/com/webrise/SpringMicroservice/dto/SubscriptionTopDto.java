package com.webrise.SpringMicroservice.dto;

import lombok.Data;

@Data
public class SubscriptionTopDto {
    private String serviceName;
    private Long count;

    public SubscriptionTopDto(String serviceName, Long count) {}
}