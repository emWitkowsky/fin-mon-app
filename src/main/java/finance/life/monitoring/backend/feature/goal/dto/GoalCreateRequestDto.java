package finance.life.monitoring.backend.feature.goal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalCreateRequestDto(
        String title,
        BigDecimal totalAmount,
        BigDecimal currentAmount,
        String description,
        LocalDate goalDate
) {
}
