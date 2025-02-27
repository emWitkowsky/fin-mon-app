package finance.life.monitoring.backend.feature.plannedTransaction.service;

import finance.life.monitoring.backend.core.handler.exception.BusinessException;
import finance.life.monitoring.backend.core.handler.exception.BusinessExceptionReason;
import finance.life.monitoring.backend.feature.plannedTransaction.dto.PlannedTransactionCreateRequestDto;
import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import finance.life.monitoring.backend.feature.plannedTransaction.repository.PlannedTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PlannedTransactionServiceDefault implements PlannedTransactionService {

    private final PlannedTransactionRepository plannedTransactionRepository;

    @Override
    public List<PlannedTransaction> getAllPlannedTransactions() {
        return plannedTransactionRepository.findAll();
    }

    @Override
    public Optional<PlannedTransaction> getPlannedTransactionById(UUID uuid) {
        return plannedTransactionRepository.findPlannedTransactionById(uuid);
    }

    @Override
    @Transactional
    public void deletePlannedTransaction(UUID uuid) {
        plannedTransactionRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public ResponseEntity<PlannedTransaction> createPlannedTransaction(PlannedTransactionCreateRequestDto plannedTransactionCreateRequestDto) {
        PlannedTransaction plannedTransaction;
        PlannedTransaction.PlannedTransactionBuilder plannedTransactionBuilder =
                PlannedTransaction.builder()
                        .title(plannedTransactionCreateRequestDto.title())
                        .type(plannedTransactionCreateRequestDto.type())
                        .amount(plannedTransactionCreateRequestDto.amount())
                        .description(plannedTransactionCreateRequestDto.description())
                        .date(plannedTransactionCreateRequestDto.date());

        plannedTransaction = plannedTransactionBuilder.build();

        PlannedTransaction savedPlannedTransaction = plannedTransactionRepository.saveAndFlush(plannedTransaction);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPlannedTransaction.getId())
                        .toUri();

        return ResponseEntity.created(location)
                .body(savedPlannedTransaction);
    }

    @Override
    @Transactional
    public ResponseEntity<PlannedTransaction> updatePlannedTransaction(UUID uuid, PlannedTransactionCreateRequestDto plannedTransactionCreateRequestDto) {
        PlannedTransaction plannedTransaction = getPlannedTransactionOrThrow(uuid);

        plannedTransaction.setTitle(plannedTransactionCreateRequestDto.title());
        plannedTransaction.setType(plannedTransactionCreateRequestDto.type());
        plannedTransaction.setAmount(plannedTransactionCreateRequestDto.amount());
        plannedTransaction.setDescription(plannedTransactionCreateRequestDto.description());
        plannedTransaction.setDate(plannedTransactionCreateRequestDto.date());

        PlannedTransaction savedPlannedTransaction = plannedTransactionRepository.saveAndFlush(plannedTransaction);

        return ResponseEntity.ok(savedPlannedTransaction);
    }

    private PlannedTransaction getPlannedTransactionOrThrow(UUID uuid) {
        Optional<PlannedTransaction> plannedTransactionOrNull = plannedTransactionRepository.findPlannedTransactionById(uuid);
        return plannedTransactionOrNull.orElseThrow(
                () -> new BusinessException(BusinessExceptionReason.PLANNED_TRANSACTION_NOT_FOUND));
    }
}
