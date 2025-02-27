package finance.life.monitoring.backend.feature.bank.controller;

import finance.life.monitoring.backend.feature.bank.dto.Bank;
import finance.life.monitoring.backend.feature.bank.model.BankCreateRequestDto;
import finance.life.monitoring.backend.feature.bank.service.BankServiceDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankServiceDefault bankService;

    @GetMapping
    public List<Bank> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping("/{id}")
    public Optional<Bank> getBankById(@PathVariable("id") UUID uuid) {
        return bankService.getBankById(uuid);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('client_admin')")
    void deleteBank(@PathVariable("id") UUID uuid) {
        bankService.deleteBank(uuid);
    }

    @PostMapping
//    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<Bank> createBank(
            @RequestParam String name
    ) {
        BankCreateRequestDto bankCreateRequestDto = new BankCreateRequestDto(
                name
        );

        return bankService.createBank(bankCreateRequestDto);
    }
}
