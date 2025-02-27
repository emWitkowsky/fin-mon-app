package finance.life.monitoring.backend.feature.plannedTransaction.controller;

import finance.life.monitoring.backend.feature.plannedTransaction.dto.PlannedTransactionCreateRequestDto;
import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import finance.life.monitoring.backend.feature.plannedTransaction.model.TransactionType;
import finance.life.monitoring.backend.feature.plannedTransaction.service.PlannedTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planned")
public class PlannedTransactionController {

    private final PlannedTransactionService plannedTransactionService;

    @GetMapping
    public List<PlannedTransaction> getAllPlannedTransactions() {
        return plannedTransactionService.getAllPlannedTransactions();
    }

    @GetMapping("/{id}")
    public Optional<PlannedTransaction> getPlannedTransactionById(@PathVariable("id") UUID uuid) {
        return plannedTransactionService.getPlannedTransactionById(uuid);
    }

    @PostMapping("/transaction")
    public ResponseEntity<PlannedTransaction> createPlannedTransaction(
            @RequestParam String title,
            @RequestParam TransactionType type,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam LocalDate date
            ) {

        PlannedTransactionCreateRequestDto plannedTransactionCreateRequestDto = new PlannedTransactionCreateRequestDto(
                title,
                type,
                amount,
                description,
                date
        );

        return plannedTransactionService.createPlannedTransaction(plannedTransactionCreateRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deletePlannedTransaction(@PathVariable("id") UUID uuid) {
        plannedTransactionService.deletePlannedTransaction(uuid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlannedTransaction> updatePlannedTransaction(
            @PathVariable("id") UUID uuid,
            @RequestParam String title,
            @RequestParam TransactionType type,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam LocalDate date
            ) {
        PlannedTransactionCreateRequestDto plannedTransactionCreateRequestDto = new PlannedTransactionCreateRequestDto(
                title, type, amount, description, date
        );

        return plannedTransactionService.updatePlannedTransaction(uuid, plannedTransactionCreateRequestDto);
    }
}
