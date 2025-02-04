package finance.life.monitoring.backend.feature.subscription.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionCreateRequestDto (
  String name,
  BigDecimal amount,
  LocalDate startDate,
  LocalDate endDate
) {}
