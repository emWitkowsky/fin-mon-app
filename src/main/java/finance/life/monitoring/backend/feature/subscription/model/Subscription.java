package finance.life.monitoring.backend.feature.subscription.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 200)
    @NotBlank
    private String name;

//    @NotBlank
    private BigDecimal amount;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
