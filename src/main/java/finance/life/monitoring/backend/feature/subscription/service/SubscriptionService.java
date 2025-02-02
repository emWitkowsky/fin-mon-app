package finance.life.monitoring.backend.feature.subscription.service;

import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionService {

    List<Subscription> getAllSubscriptions();

    Optional<Subscription> getSubscription(UUID id);

    ResponseEntity<Subscription> createSubscription(SubscriptionCreateRequestDto subscriptionCreateRequestDto);

    Optional<Float> getTotalOfSubscriptions();
}
