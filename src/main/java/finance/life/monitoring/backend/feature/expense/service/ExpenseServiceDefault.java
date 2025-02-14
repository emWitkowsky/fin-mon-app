package finance.life.monitoring.backend.feature.expense.service;

import finance.life.monitoring.backend.core.handler.exception.BusinessException;
import finance.life.monitoring.backend.core.handler.exception.BusinessExceptionReason;
import finance.life.monitoring.backend.feature.bank.dto.Bank;
import finance.life.monitoring.backend.feature.bank.repository.BankRepository;
import finance.life.monitoring.backend.feature.expense.dto.ExpenseCreateRequestDto;
import finance.life.monitoring.backend.feature.expense.dto.ExpenseResponseDto;
import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.feature.expense.repository.ExpenseRepository;
import finance.life.monitoring.backend.shared.dto.ResponseDto;
import finance.life.monitoring.backend.shared.enums.SuccessCode;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseServiceDefault implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BankRepository bankRepository;

    public ExpenseServiceDefault(ExpenseRepository expenseRepository, BankRepository bankRepository) {
        this.expenseRepository = expenseRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto<ExpenseResponseDto>> createExpense(ExpenseCreateRequestDto createExpenseRequestDto) {
        Expense expense;
//        Bank bank = bankRepository.findByName(createExpenseRequestDto.bank);
        Expense.ExpenseBuilder expenseBuilder =
                Expense.builder()
                        .title(createExpenseRequestDto.title())
                        .summary(createExpenseRequestDto.summary())
                        .transactionDate(createExpenseRequestDto.transactionDate())
                        .bookDate(createExpenseRequestDto.bookDate())
                        .description(createExpenseRequestDto.description())
                        .category(createExpenseRequestDto.category())
                        .details(createExpenseRequestDto.expenseDetails());

        expense = expenseBuilder.build();

        Expense savedExpense = expenseRepository.saveAndFlush(expense);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedExpense.getId())
                        .toUri();

        return ResponseEntity.ok(
                new ResponseDto<>(
                        SuccessCode.RESOURCE_CREATED,
                        "Successfully created expense",
                        new ArrayList<>(),
                        ExpenseResponseDto.from(expense)));

    }

    @Override
    public ResponseEntity<ResponseDto<ExpenseResponseDto>> getExpenseById(UUID id) {
        Expense expense = getExpenseOrThrow(id);
//        Optional<Expense> expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        SuccessCode.RESPONSE_SUCCESSFUL,
                        "Successfully fetched expense with id " + id,
                        new ArrayList<>(),
                        ExpenseResponseDto.from(expense)));
    }

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto<Void>> deleteExpense(UUID id) {
        Expense expenseToDelete = getExpenseOrThrow(id);
        expenseRepository.delete(expenseToDelete);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        SuccessCode.RESOURCE_DELETED,
                        String.format("Successfully deleted news with id %s", id),
                        new ArrayList<>(),
                        null));
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto<ExpenseResponseDto>> updateExpense(ExpenseCreateRequestDto expenseCreateRequestDto, UUID id) {
        Expense expense = getExpenseOrThrow(id);

        expense.setTitle(expenseCreateRequestDto.title());
        expense.setSummary(expenseCreateRequestDto.summary());
        expense.setTransactionDate(expenseCreateRequestDto.transactionDate());
        expense.setBookDate(expenseCreateRequestDto.bookDate());
        expense.setDescription(expenseCreateRequestDto.description());
        expense.setCategory(expenseCreateRequestDto.category());
        expense.getDetails().setPaymentMethod(expenseCreateRequestDto.expenseDetails().getPaymentMethod());
        expense.getDetails().setCurrency(expenseCreateRequestDto.expenseDetails().getCurrency());
        expense.getDetails().setAmount(expenseCreateRequestDto.expenseDetails().getAmount());

        expenseRepository.saveAndFlush(expense);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        SuccessCode.RESOURCE_UPDATED,
                        "Successfully updated expense with id " + id,
                        new ArrayList<>(),
                        ExpenseResponseDto.from(expense)));
    }

    private Expense getExpenseOrThrow(UUID id) {
        Optional<Expense> newsOrNull = expenseRepository.getExpenseById(id);
        return newsOrNull.orElseThrow(
                () -> new BusinessException(BusinessExceptionReason.EXPENSE_NOT_FOUND));
    }

//    public List<Expense> getExpensesByBankAndDateRange(String bankName, LocalDate startDate, LocalDate endDate) {
//        return expenseRepository.findByBankAndDateRange(bankName, startDate, endDate);
//    }

    public List<Expense> getExpensesByCategoryAndDateRange(ExpenseCategory category, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByCategoryAndDateRange(category, startDate, endDate);
    }
}
