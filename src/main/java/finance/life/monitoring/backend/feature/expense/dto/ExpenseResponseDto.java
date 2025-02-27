package finance.life.monitoring.backend.feature.expense.dto;

import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.feature.expense.model.ExpenseDetails;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDto(
        UUID id,
        String title,
        String summary,
        LocalDate transactionDate,
        LocalDate bookDate,
        String description,
        ExpenseCategory category,
        ExpenseDetails expenseDetails) {

        public static ExpenseResponseDto from(@Nonnull Expense expense) {
            return new ExpenseResponseDto(
                    expense.getId(),
                    expense.getTitle(),
                    expense.getSummary(),
                    expense.getTransactionDate(),
                    expense.getBookDate(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getDetails());
        }
}
