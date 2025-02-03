package finance.life.monitoring.backend.feature.subscription.controller;

import finance.life.monitoring.backend.feature.subscription.dto.SubscriptionCreateRequestDto;
import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import finance.life.monitoring.backend.feature.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
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
    public Optional<Subscription> getSubscription(@PathVariable("id") UUID id) {
        return subscriptionService.getSubscription(id);
    }

    @GetMapping("/subs/total")
    public Optional<Float> getTotalAmountOfSubscriptions() {
        return subscriptionService.getTotalOfSubscriptions();
    }

    @PostMapping("/subs")
    public ResponseEntity<Subscription> createSubscription(
            @RequestParam String name,
            @RequestParam Float amount,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate
            ) {
        SubscriptionCreateRequestDto subscriptionCreateRequestDto = new SubscriptionCreateRequestDto(
                name,
                amount,
                startDate,
                endDate
        );

        return subscriptionService.createSubscription(subscriptionCreateRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable("id") UUID uuid) {
        subscriptionService.deleteSubscription(uuid);
    }
}
