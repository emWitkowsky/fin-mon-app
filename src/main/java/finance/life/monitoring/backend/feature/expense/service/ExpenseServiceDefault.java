package finance.life.monitoring.backend.feature.expense.service;

import finance.life.monitoring.backend.feature.expense.dto.CreateExpenseRequestDto;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.feature.expense.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseServiceDefault implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceDefault(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Expense> createExpense(CreateExpenseRequestDto createExpenseRequestDto) {
        Expense expense;
        Expense.ExpenseBuilder expenseBuilder =
                Expense.builder()
                        .title(createExpenseRequestDto.title())
                        .summary(createExpenseRequestDto.summary())
                        .transactionDate(createExpenseRequestDto.transactionDate())
                        .bookDate(createExpenseRequestDto.bookDate())
                        .description(createExpenseRequestDto.description());

        expense = expenseBuilder.build();

        Expense savedExpense = expenseRepository.saveAndFlush(expense);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedExpense.getId())
                        .toUri();

        return ResponseEntity.created(location)
                .body(savedExpense);

    }

    @Override
    public ResponseEntity<Expense> getExpenseById(UUID id) {
        Expense expense = getExpenseOrThrow(id);
        return ResponseEntity.ok().body(expense);
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public void deleteExpense(UUID id) {
        Expense expenseToDelete = getExpenseOrThrow(id);
        expenseRepository.delete(expenseToDelete);
    }

    @Override
    public ResponseEntity<Expense> updateExpense(UUID id) {
        return null;
    }

    private Expense getExpenseOrThrow(UUID id) {
        Optional<Expense> newsOrNull = expenseRepository.getExpenseById(id);
        return newsOrNull.orElseThrow(
                () -> new RuntimeException("Expense not found"));
    }
}
