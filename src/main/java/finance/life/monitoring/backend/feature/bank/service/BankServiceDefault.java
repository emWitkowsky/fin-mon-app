package finance.life.monitoring.backend.feature.bank.service;


import finance.life.monitoring.backend.feature.bank.dto.Bank;
import finance.life.monitoring.backend.feature.bank.model.BankCreateRequestDto;
import finance.life.monitoring.backend.feature.bank.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankServiceDefault implements BankService {

    private final BankRepository bankRepository;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public ResponseEntity<Bank> createBank(BankCreateRequestDto bankCreateRequestDto) {
        Bank bank;
        Bank.BankBuilder bankBuilder =
                Bank.builder()
                        .name(bankCreateRequestDto.name());

        bank = bankBuilder.build();

        Bank savedBank = bankRepository.saveAndFlush(bank);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedBank.getId())
                        .toUri();

        return ResponseEntity.created(location)
                .body(savedBank);
    }

    @Override
    public Optional<Bank> getBankById(UUID uuid) {
        return bankRepository.findById(uuid);
    }

    @Override
    public void deleteBank(UUID uuid) {
        bankRepository.deleteById(uuid);
    }
}
