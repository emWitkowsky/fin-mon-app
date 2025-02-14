package finance.life.monitoring.backend.feature.expense.repository;

import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Optional<Expense> getExpenseById(UUID id);

//    @Query("SELECT e FROM Expense e INNER JOIN e.category c WHERE c.name = :categoryName AND e.transactionDate BETWEEN :startDate AND :endDate")
//    List<Expense> findByCategoryAndDateRange(@Param("categoryName") ExpenseCategory categoryName,
//                                             @Param("startDate") LocalDate startDate,
//                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Expense e WHERE e.category = :category AND e.transactionDate BETWEEN :startDate AND :endDate")
    List<Expense> findByCategoryAndDateRange(@Param("category") ExpenseCategory category,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);


//    @Query("SELECT e FROM Expense e INNER JOIN e.bank b WHERE b.name = :bankName AND e.transactionDate BETWEEN :startDate AND :endDate")
//    List<Expense> findByBankAndDateRange(@Param("bankName") String bankName,
//                                         @Param("startDate") LocalDate startDate,
//                                         @Param("endDate") LocalDate endDate);

}
