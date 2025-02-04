package finance.life.monitoring.backend.feature.bank.service;

import finance.life.monitoring.backend.feature.bank.dto.Bank;
import finance.life.monitoring.backend.feature.bank.model.BankCreateRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankService {

    List<Bank> getAllBanks();

    ResponseEntity<Bank> createBank(BankCreateRequestDto bankCreateRequestDto);

    Optional<Bank> getBankById(UUID uuid);

    void deleteBank(UUID uuid);
}
