package finance.life.monitoring.backend.feature.plannedTransaction.repository;

import finance.life.monitoring.backend.feature.plannedTransaction.model.PlannedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlannedTransactionRepository extends JpaRepository<PlannedTransaction, UUID> {

    Optional<PlannedTransaction> findPlannedTransactionById(UUID uuid);
}
