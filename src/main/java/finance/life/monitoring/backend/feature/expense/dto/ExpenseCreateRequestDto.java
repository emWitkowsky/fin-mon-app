package finance.life.monitoring.backend.feature.expense.dto;

import finance.life.monitoring.backend.feature.bank.dto.Bank;
import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.ExpenseDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseCreateRequestDto(
    String title,
    String summary,
    LocalDate transactionDate,
    LocalDate bookDate,
    String description,
    ExpenseCategory category,
//    BankDto bank
    ExpenseDetails expenseDetails

) {}
