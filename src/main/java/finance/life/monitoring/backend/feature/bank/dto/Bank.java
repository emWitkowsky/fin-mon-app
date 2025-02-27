package finance.life.monitoring.backend.feature.bank.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "bank", schema = "public")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id", nullable = false)
    private UUID id;

    @NotBlank
    private String name;

//    @OneToMany(targetEntity = Expense.class, mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Expense> expense;
}
