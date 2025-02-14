package finance.life.monitoring.backend.Subscription.mock;

import finance.life.monitoring.backend.feature.subscription.model.Subscription;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class SubscriptionMockData {

    public static UUID generateId() {
        return UUID.randomUUID();
    }

    public static LocalDate generateDateTime() {
        return LocalDate.of(2023, 10, 1);
    }

    public static Subscription generateSubscription() {
        UUID id = generateId();
        LocalDate dateTime = generateDateTime();
        BigDecimal bd1 = new BigDecimal(10);
        Subscription subscription = new Subscription();
        subscription.setName("Mock Name");
        subscription.setAmount(bd1);
        subscription.setId(id);
        subscription.setStartDate(dateTime);
        subscription.setEndDate(dateTime);
        return subscription;
    }

}
