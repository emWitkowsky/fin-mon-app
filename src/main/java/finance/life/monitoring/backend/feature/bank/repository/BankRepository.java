package finance.life.monitoring.backend.feature.bank.repository;

import finance.life.monitoring.backend.feature.bank.dto.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
}
