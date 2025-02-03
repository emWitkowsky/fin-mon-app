package finance.life.monitoring.backend.feature.goal.service;

import finance.life.monitoring.backend.feature.goal.dto.GoalCreateRequestDto;
import finance.life.monitoring.backend.feature.goal.model.Goal;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoalService {

    List<Goal> getAllGoals();

    ResponseEntity<Goal> createGoal(GoalCreateRequestDto goalCreateRequestDto);

    Optional<Goal> getGoalById(UUID uuid);

    void deleteGoal(UUID uuid);

    int updateGoalCurrentAmount(UUID uuid, BigDecimal amount);
}
