package finance.life.monitoring.backend.feature.subscription.repository;

import finance.life.monitoring.backend.feature.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    Optional<Subscription> getSubscriptionById(UUID uuid);

    @Query("SELECT SUM(s.amount) FROM Subscription s")
    Optional<BigDecimal> getTotalOfSubscriptions();
}
