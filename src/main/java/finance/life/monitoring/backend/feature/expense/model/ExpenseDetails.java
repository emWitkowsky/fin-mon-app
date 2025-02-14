package finance.life.monitoring.backend.feature.expense.model;

import finance.life.monitoring.backend.feature.expense.enums.PaymentMethod;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDetails {

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Size(min = 3, max = 3)
    private String currency;

    @DecimalMin("0.0")
    private BigDecimal amount;
}

