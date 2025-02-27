package finance.life.monitoring.backend.feature.expense.model;

import finance.life.monitoring.backend.feature.expense.enums.ExpenseCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDate;
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
    private LocalDate transactionDate;

    @Column(updatable = false)
//    @CreationTimestamp  Not sure we need that? but keep it just in case
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate bookDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT")
    private ExpenseCategory category;

//    @ManyToOne
//    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
//    private Bank bank;

    @Embedded
    private ExpenseDetails details;
}
