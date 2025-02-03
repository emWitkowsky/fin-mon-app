package finance.life.monitoring.backend.feature.plannedTransaction.dto;

import finance.life.monitoring.backend.feature.plannedTransaction.model.TransactionType;

import java.time.LocalDateTime;

public record PlannedTransactionCreateRequestDto(

        String title,
        TransactionType type,
        Float amount,
        String description,
        LocalDateTime date
) {
}
