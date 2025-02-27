package finance.life.monitoring.backend.feature.subscription.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SubscriptionCreateRequestDto (
  String name,
  BigDecimal amount,
  LocalDate startDate,
  LocalDate endDate
) {}
