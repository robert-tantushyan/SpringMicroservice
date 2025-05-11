package com.webrise.SpringMicroservice.service;

import com.webrise.SpringMicroservice.dto.SubscriptionDto;
import com.webrise.SpringMicroservice.dto.SubscriptionTopDto;
import com.webrise.SpringMicroservice.exception.ResourceNotFoundException;
import com.webrise.SpringMicroservice.model.Subscription;
import com.webrise.SpringMicroservice.model.User;
import com.webrise.SpringMicroservice.repository.SubscriptionRepository;
import com.webrise.SpringMicroservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionDto addSubscription(Long userId, SubscriptionDto subscriptionDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Subscription subscription = modelMapper.map(subscriptionDto, Subscription.class);
        subscription.setUser(user);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        logger.info("Added subscription with id: {} for user: {}", savedSubscription.getId(), userId);
        return modelMapper.map(savedSubscription, SubscriptionDto.class);
    }

    public List<SubscriptionDto> getUserSubscriptions(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionDto.class))
                .collect(Collectors.toList());
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + subscriptionId));

        if (!subscription.getUser().getId().equals(userId)) {
            logger.error("Subscription {} does not belong to user {}", subscriptionId, userId);
            throw new ResourceNotFoundException("Subscription not found for user with id: " + userId);
        }

        subscriptionRepository.delete(subscription);
        logger.info("Deleted subscription with id: {} for user: {}", subscriptionId, userId);
    }

    public List<SubscriptionTopDto> getTop3PopularSubscriptions() {
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> new SubscriptionTopDto(
                        (String) result[0],
                        (Long) result[1]
                ))
                .collect(Collectors.toList());
    }
}
