package finance.life.monitoring.backend.feature.plannedTransaction.model;

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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlannedTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 200)
    @NotBlank
    private String title;

    private TransactionType type;

//    @NotBlank
    private Float amount;

    @Size(max = 200)
    @NotBlank
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime date;

}
