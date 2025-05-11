package com.webrise.SpringMicroservice.repository;

import com.webrise.SpringMicroservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Query("SELECT s.serviceName as serviceName, COUNT(s) as count FROM Subscription s GROUP BY s.serviceName ORDER BY COUNT(s) DESC LIMIT 3")
    List<Object[]> findTop3PopularSubscriptions();

}