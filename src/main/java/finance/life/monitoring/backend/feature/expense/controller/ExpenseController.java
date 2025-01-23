package finance.life.monitoring.backend.feature.expense.controller;

import finance.life.monitoring.backend.feature.expense.dto.ExpenseCreateRequestDto;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.feature.expense.service.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/expenses/test")
    public String showExpenses() {
        return "Show expenses GET method";
    }

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getAllExpenses(@PathVariable("id") UUID id) {
        return expenseService.getExpenseById(id);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable("id") UUID id) {
        expenseService.deleteExpense(id);
    }

    @PostMapping()
    public ResponseEntity<Expense>  createExpense(
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam LocalDateTime transactionDate,
            @RequestParam LocalDateTime bookDate,
            @RequestParam String description
            ) {
        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
                title,
                summary,
                transactionDate,
                bookDate,
                description
        );
        return expenseService.createExpense(createExpenseRequestDto);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Expense>  updateExpenseById(
            @RequestParam String title,
            @RequestParam String summary,
            @RequestParam LocalDateTime transactionDate,
            @RequestParam LocalDateTime bookDate,
            @RequestParam String description,
            @PathVariable("id") UUID id
    ) {
        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
                title,
                summary,
                transactionDate,
                bookDate,
                description
        );
        return expenseService.updateExpense(createExpenseRequestDto, id);
    }
}
