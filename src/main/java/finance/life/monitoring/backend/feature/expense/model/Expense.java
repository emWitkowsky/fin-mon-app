package finance.life.monitoring.backend.feature.expense.model;

import jakarta.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 200)
    @NotBlank
    private String title;

    @Size(min = 3, max = 600)
    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String summary;

    @Column(updatable = false)
//    @CreationTimestamp  Not sure we need that? but keep it just in case
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime transactionDate;

    @Column(updatable = false)
//    @CreationTimestamp  Not sure we need that? but keep it just in case
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime bookDate;

    @Column(columnDefinition = "TEXT")
    private String description;


}
