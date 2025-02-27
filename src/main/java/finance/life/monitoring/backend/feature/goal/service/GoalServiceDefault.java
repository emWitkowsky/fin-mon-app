package finance.life.monitoring.backend.feature.goal.service;

import finance.life.monitoring.backend.core.handler.exception.BusinessException;
import finance.life.monitoring.backend.core.handler.exception.BusinessExceptionReason;
import finance.life.monitoring.backend.feature.goal.dto.GoalCreateRequestDto;
import finance.life.monitoring.backend.feature.goal.model.Goal;
import finance.life.monitoring.backend.feature.goal.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GoalServiceDefault implements GoalService {

    private final GoalRepository goalRepository;

    @Override
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<Goal> createGoal(GoalCreateRequestDto goalCreateRequestDto) {
        Goal goal;
        Goal.GoalBuilder goalBuilder =
                Goal.builder()
                        .title(goalCreateRequestDto.title())
                        .totalAmount(goalCreateRequestDto.totalAmount())
                        .currentAmount(goalCreateRequestDto.currentAmount())
                        .description(goalCreateRequestDto.description())
                        .goalDate(goalCreateRequestDto.goalDate());

        goal = goalBuilder.build();

        Goal savedGoal = goalRepository.saveAndFlush(goal);

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedGoal.getId())
                        .toUri();

        return ResponseEntity.created(location)
                .body(savedGoal);
    }

    @Override
    public Optional<Goal> getGoalById(UUID uuid) {
        return goalRepository.findById(uuid);
    }

    @Override
    public void deleteGoal(UUID uuid) {
        goalRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public int updateGoalCurrentAmount(UUID uuid, BigDecimal amount) {
        Goal goal = goalRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));

        return goalRepository.addAmountToGoal(goal.getId(), amount);
    }

    @Override
    public ResponseEntity<Goal> updateGoal(UUID uuid, GoalCreateRequestDto goalCreateRequestDto) {
        Goal goal = getGoalOrThrow(uuid);
        goal.setTitle(goalCreateRequestDto.title());
        goal.setTotalAmount(goalCreateRequestDto.totalAmount());
        goal.setCurrentAmount(goalCreateRequestDto.currentAmount());
        goal.setDescription(goalCreateRequestDto.description());
        goal.setGoalDate(goalCreateRequestDto.goalDate());

        Goal savedGoal = goalRepository.saveAndFlush(goal);
        return ResponseEntity.ok(savedGoal);
    }

    private Goal getGoalOrThrow(UUID uuid) {
        Optional<Goal> goalOrNull = goalRepository.getGoalById(uuid);
        return goalOrNull.orElseThrow(
                () -> new BusinessException(BusinessExceptionReason.GOAL_NOT_FOUND));
    }

}
