package finance.life.monitoring.backend.feature.expense.service;

import finance.life.monitoring.backend.feature.expense.dto.ExpenseCreateRequestDto;
import finance.life.monitoring.backend.feature.expense.dto.ExpenseResponseDto;
import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.shared.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ResponseEntity<ResponseDto<ExpenseResponseDto>> createExpense(ExpenseCreateRequestDto createExpenseRequestDto);

    ResponseEntity<ResponseDto<ExpenseResponseDto>> getExpenseById(UUID id);

    List<Expense> getExpenses();

    ResponseEntity<ResponseDto<Void>> deleteExpense(UUID id);

    ResponseEntity<ResponseDto<ExpenseResponseDto>> updateExpense(ExpenseCreateRequestDto expenseCreateRequestDto, UUID id);

//    List<Expense> getExpensesByBankAndDateRange(String bankName, LocalDate startDate, LocalDate endDate);
    List<Expense> getExpensesByCategoryAndDateRange(ExpenseCategory category, LocalDate startDate, LocalDate endDate);
}
