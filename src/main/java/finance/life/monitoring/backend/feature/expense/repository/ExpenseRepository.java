package finance.life.monitoring.backend.feature.expense.repository;

import finance.life.monitoring.backend.feature.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Optional<Expense> getExpenseById(UUID id);
}
