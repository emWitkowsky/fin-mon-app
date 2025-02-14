package finance.life.monitoring.backend.feature.subscription.controller;

import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import finance.life.monitoring.backend.feature.subscription.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public List<Subscription> getAllSubscriptions() {
      return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public Subscription getSubscription(@PathVariable("id") UUID id) {
        return subscriptionService.getSubscription(id);
    }

    @GetMapping("/subs/total")
    public Optional<BigDecimal> getTotalAmountOfSubscriptions() {
        return subscriptionService.getTotalOfSubscriptions();
    }

    @PostMapping("/subs")
    public ResponseEntity<Subscription> createSubscription(
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            ) {
        SubscriptionCreateRequestDto subscriptionCreateRequestDto = new SubscriptionCreateRequestDto(
                name,
                amount,
                startDate,
                endDate
        );

        log.info("Received subscription: {}", subscriptionCreateRequestDto);
        return subscriptionService.createSubscription(subscriptionCreateRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable("id") UUID uuid) {
        subscriptionService.deleteSubscription(uuid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(
            @PathVariable("id") UUID uuid,
            @RequestParam String name,
            @RequestParam BigDecimal amount,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
            ) {
        SubscriptionCreateRequestDto subscriptionCreateRequestDto = new SubscriptionCreateRequestDto(
                name,
                amount,
                startDate,
                endDate
        );

        return subscriptionService.updateSubscription(uuid, subscriptionCreateRequestDto);
    }

    @GetMapping("/sortedByEndDate")
    List<Subscription> getAllSubscriptionsSortedByEndDate() {
        return subscriptionService.getAllSubscriptionsSortedByEndDate();
    }

    @GetMapping("/sortedBy")
    List<String> getAllSubscriptionsSortedBy() {
        return subscriptionService.getAllSubscriptionsSortedBy();
    }
}
