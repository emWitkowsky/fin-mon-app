package finance.life.monitoring.backend.feature.plannedTransaction.service;

import finance.life.monitoring.backend.feature.plannedTransaction.dto.PlannedTransactionCreateRequestDto;
import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlannedTransactionService {

    List<PlannedTransaction> getAllPlannedTransactions();

    Optional<PlannedTransaction> getPlannedTransactionById(UUID uuid);

    void deletePlannedTransaction(UUID uuid);

    ResponseEntity<PlannedTransaction> createPlannedTransaction(PlannedTransactionCreateRequestDto plannedTransactionCreateRequestDto);
}
