package finance.life.monitoring.backend.feature.expense.controller;

import finance.life.monitoring.backend.feature.expense.dto.ExpenseCreateRequestDto;
import finance.life.monitoring.backend.feature.expense.dto.ExpenseResponseDto;
import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import finance.life.monitoring.backend.feature.expense.model.Expense;
import finance.life.monitoring.backend.feature.expense.service.ExpenseService;
import finance.life.monitoring.backend.shared.dto.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/expenses/test")
    public String showExpenses() {
        return "Show expenses GET method";
    }

    @GetMapping("")
    public List<Expense> getAllExpenses() {
        return expenseService.getExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ExpenseResponseDto>> getExpenseById(@PathVariable("id") UUID id) {
        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteExpense(@PathVariable("id") UUID id) {
        return expenseService.deleteExpense(id);
    }

//    @PostMapping()
//    public ResponseEntity<ResponseDto<ExpenseResponseDto>>  createExpense(
//            @RequestParam String title,
//            @RequestParam String summary,
//            @RequestParam LocalDate transactionDate,
//            @RequestParam LocalDate bookDate,
//            @RequestParam String description,
//            @RequestParam ExpenseCategory category
//            ) {
//        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
//                title,
//                summary,
//                transactionDate,
//                bookDate,
//                description,
//                category
//        );
//        return expenseService.createExpense(createExpenseRequestDto);
//    }

    @PostMapping()
    public ResponseEntity<ResponseDto<ExpenseResponseDto>>  createExpense(
            @RequestBody ExpenseCreateRequestDto expenseCreateRequestDto
    ) {
//        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
//                title,
//                summary,
//                transactionDate,
//                bookDate,
//                description,
//                category
//        );
        return expenseService.createExpense(expenseCreateRequestDto);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseDto<ExpenseResponseDto>>  updateExpenseById(
//            @RequestParam String title,
//            @RequestParam String summary,
//            @RequestParam LocalDate transactionDate,
//            @RequestParam LocalDate bookDate,
//            @RequestParam String description,
//            @RequestParam ExpenseCategory category,
//            @PathVariable("id") UUID id
//    ) {
//        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
//                title,
//                summary,
//                transactionDate,
//                bookDate,
//                description,
//                category
//        );
//        return expenseService.updateExpense(createExpenseRequestDto, id);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ExpenseResponseDto>>  updateExpenseById(
            @PathVariable("id") UUID id,
            @RequestBody ExpenseCreateRequestDto requestDto
    ) {
//        ExpenseCreateRequestDto createExpenseRequestDto = new ExpenseCreateRequestDto(
//                requestDto.title(),
//                requestDto.summary(),
//                requestDto.transactionDate(),
//                requestDto.bookDate(),
//                requestDto.description(),
//                requestDto.category(),
//                requestDto.bank()
//        );
        return expenseService.updateExpense(requestDto, id);
    }


//    @GetMapping("/by-bank")
//    public ResponseEntity<List<Expense>> getExpensesByBankAndDateRange(
//            @RequestParam String bankName,
//            @RequestParam  LocalDate startDate,
//            @RequestParam  LocalDate endDate) {
//
//        List<Expense> expenses = expenseService.getExpensesByBankAndDateRange(bankName, startDate, endDate);
//        return ResponseEntity.ok(expenses);
//    }

    @GetMapping("/category-date-range")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndDateRange(
            @RequestParam("category") ExpenseCategory category,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        List<Expense> expenses = expenseService.getExpensesByCategoryAndDateRange(category, startDate, endDate);

        return ResponseEntity.ok(expenses);
    }
}
