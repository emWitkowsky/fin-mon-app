package finance.life.monitoring.backend.feature.plannedTransaction.service;

import finance.life.monitoring.backend.feature.plannedTransaction.dto.PlannedTransactionCreateRequestDto;
import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import finance.life.monitoring.backend.feature.plannedTransaction.repository.PlannedTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlannedTransactionServiceDefault implements PlannedTransactionService {

    private final PlannedTransactionRepository plannedTransactionRepository;

    public PlannedTransactionServiceDefault(PlannedTransactionRepository plannedTransactionRepository) {
        this.plannedTransactionRepository = plannedTransactionRepository;
    }

    @Override
    public List<PlannedTransaction> getAllPlannedTransactions() {
        return plannedTransactionRepository.findAll();
    }

    @Override
    public Optional<PlannedTransaction> getPlannedTransactionById(UUID uuid) {
        return plannedTransactionRepository.findPlannedTransactionById(uuid);
    }

    @Override
    public void deletePlannedTransaction(UUID uuid) {
        plannedTransactionRepository.deleteById(uuid);
    }

    @Override
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
}
