package finance.life.monitoring.backend.feature.subscription.dto;

import java.time.LocalDateTime;

public record SubscriptionCreateRequestDto (
  String name,
  Float amount,
  LocalDateTime startDate,
  LocalDateTime endDate
) {}
