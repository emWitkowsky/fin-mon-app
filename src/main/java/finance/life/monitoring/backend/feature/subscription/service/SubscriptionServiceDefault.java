package finance.life.monitoring.backend.feature.subscription.service;

import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import finance.life.monitoring.backend.feature.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionServiceDefault implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceDefault(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> getSubscription(UUID id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public ResponseEntity<Subscription> createSubscription(SubscriptionCreateRequestDto subscriptionCreateRequestDto) {
        Subscription subscription;
        Subscription.SubscriptionBuilder subscriptionBuilder =
                Subscription.builder()
                        .name(subscriptionCreateRequestDto.name())
                        .amount(subscriptionCreateRequestDto.amount())
                        .startDate(subscriptionCreateRequestDto.startDate())
                        .endDate(subscriptionCreateRequestDto.endDate());

        subscription = subscriptionBuilder.build();

        Subscription savedSubscription = subscriptionRepository.saveAndFlush(subscription);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedSubscription.getId())
                        .toUri();

        return ResponseEntity.created(location)
                .body(savedSubscription);

    }

    @Override
    public Optional<Float> getTotalOfSubscriptions() {
        Optional<Float> totalAmount = subscriptionRepository.getTotalOfSubscriptions();
        return totalAmount;
    }
}
