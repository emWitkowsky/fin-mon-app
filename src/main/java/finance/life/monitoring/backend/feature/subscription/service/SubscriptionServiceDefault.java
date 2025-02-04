package finance.life.monitoring.backend.feature.subscription.service;

import finance.life.monitoring.backend.core.handler.exception.BusinessException;
import finance.life.monitoring.backend.core.handler.exception.BusinessExceptionReason;
import finance.life.monitoring.backend.feature.plannedTransaction.dto.PlannedTransactionCreateRequestDto;
import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import finance.life.monitoring.backend.feature.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
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
    public Optional<BigDecimal> getTotalOfSubscriptions() {
        return subscriptionRepository.getTotalOfSubscriptions();
    }

    @Override
    public void deleteSubscription(UUID uuid) {
        subscriptionRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public ResponseEntity<Subscription> updateSubscription(UUID uuid, SubscriptionCreateRequestDto subscriptionCreateRequestDto) {
        Subscription subscription = getSubscriptionOrThrow(uuid);

        subscription.setName(subscriptionCreateRequestDto.name());
        subscription.setAmount(subscriptionCreateRequestDto.amount());
        subscription.setStartDate(subscriptionCreateRequestDto.startDate());
        subscription.setEndDate(subscriptionCreateRequestDto.endDate());

        Subscription savedSubscription = subscriptionRepository.saveAndFlush(subscription);

        return ResponseEntity.ok(savedSubscription);
    }

    private Subscription getSubscriptionOrThrow(UUID uuid) {
        Optional<Subscription> subscriptionOrNull = subscriptionRepository.getSubscriptionById(uuid);
        return subscriptionOrNull.orElseThrow(
                () -> new BusinessException(BusinessExceptionReason.SUBSCRIPTION_NOT_FOUND));
    }
}
