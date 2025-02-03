package finance.life.monitoring.backend.feature.goal.repository;

import finance.life.monitoring.backend.feature.goal.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    @Modifying
    @Query("UPDATE Goal g SET g.currentAmount = g.currentAmount + :amount WHERE g.id = :uuid")
    int addAmountToGoal(UUID uuid, BigDecimal amount);

    Optional<Goal> getGoalById(UUID uuid);
}
