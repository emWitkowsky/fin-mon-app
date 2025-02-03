package finance.life.monitoring.backend.feature.goal.service;

import finance.life.monitoring.backend.feature.goal.dto.GoalCreateRequestDto;
import finance.life.monitoring.backend.feature.goal.model.Goal;
import finance.life.monitoring.backend.feature.goal.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoalServiceDefault implements GoalService {

    private final GoalRepository goalRepository;

    public GoalServiceDefault(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

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
}
