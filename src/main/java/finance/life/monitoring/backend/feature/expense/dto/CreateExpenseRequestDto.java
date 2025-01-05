package finance.life.monitoring.backend.feature.expense.dto;

import java.time.LocalDateTime;

public record CreateExpenseRequestDto (
    String title,
    String summary,
    LocalDateTime transactionDate,
    LocalDateTime bookDate,
    String description

) {}
