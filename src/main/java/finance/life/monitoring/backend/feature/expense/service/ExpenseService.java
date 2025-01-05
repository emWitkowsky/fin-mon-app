package finance.life.monitoring.backend.feature.expense.service;

import finance.life.monitoring.backend.feature.expense.dto.CreateExpenseRequestDto;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ResponseEntity<Expense> createExpense(CreateExpenseRequestDto createExpenseRequestDto);

    ResponseEntity<Expense> getExpenseById(UUID id);

    List<Expense> getExpenses();

    void deleteExpense(UUID id);

    ResponseEntity<Expense> updateExpense(UUID id);

}
