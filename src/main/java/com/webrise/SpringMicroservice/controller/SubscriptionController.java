package com.webrise.SpringMicroservice.controller;

import com.webrise.SpringMicroservice.dto.SubscriptionDto;
import com.webrise.SpringMicroservice.dto.SubscriptionTopDto;
import com.webrise.SpringMicroservice.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionDto> addSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto subscription = subscriptionService.addSubscription(id, subscriptionDto);
        return ResponseEntity.created(URI.create("/users/" + id + "/subscriptions/" + subscription.getId()))
                .body(subscription);
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long id,
            @PathVariable Long subId) {
        subscriptionService.deleteSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<SubscriptionTopDto>> getTop3PopularSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTop3PopularSubscriptions());
    }
}