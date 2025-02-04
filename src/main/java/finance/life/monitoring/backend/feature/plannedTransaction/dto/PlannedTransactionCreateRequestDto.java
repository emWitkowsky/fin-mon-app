package finance.life.monitoring.backend.feature.plannedTransaction.dto;

import finance.life.monitoring.backend.feature.plannedTransaction.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlannedTransactionCreateRequestDto(

        String title,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDate date
) {
}
