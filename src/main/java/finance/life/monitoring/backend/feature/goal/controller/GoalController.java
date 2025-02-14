//package finance.life.monitoring.backend.feature.goal.controller;
//
//import finance.life.monitoring.backend.feature.goal.dto.GoalCreateRequestDto;
//import finance.life.monitoring.backend.feature.goal.model.Goal;
//import finance.life.monitoring.backend.feature.goal.service.GoalService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.swing.text.html.Option;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/goal")
//public class GoalController {
//
//    private final GoalService goalService;
//
//    public GoalController(GoalService goalService) {
//        this.goalService = goalService;
//    }
//
//    @GetMapping
//    public List<Goal> getAllGoals() {
//        return goalService.getAllGoals();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Goal> getGoalById(@PathVariable("id")UUID uuid) {
//        return goalService.getGoalById(uuid);
//    }
//
//    @PostMapping
//    public ResponseEntity<Goal> createGoal(
//            @RequestParam String title,
//            @RequestParam BigDecimal totalAmount,
//            @RequestParam BigDecimal currentAmount,
//            @RequestParam String description,
//            @RequestParam LocalDate goalDate
//            ) {
//        GoalCreateRequestDto goalCreateRequestDto = new GoalCreateRequestDto(
//                title, totalAmount, currentAmount, description, goalDate
//        );
//
//        return goalService.createGoal(goalCreateRequestDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteGoal(@PathVariable("id") UUID uuid) {
//        goalService.deleteGoal(uuid);
//    }
//
//    @PatchMapping("/{id}/deposit")
//    public int updateCurrentAmount(@PathVariable("id") UUID uuid, @RequestParam BigDecimal amount) {
//        return goalService.updateGoalCurrentAmount(uuid, amount);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Goal> updateGoal(
//            @PathVariable("id") UUID uuid,
//            @RequestParam String title,
//            @RequestParam BigDecimal totalAmount,
//            @RequestParam BigDecimal currentAmount,
//            @RequestParam String description,
//            @RequestParam LocalDate goalDate
//            ) {
//        GoalCreateRequestDto goalCreateRequestDto = new GoalCreateRequestDto(
//                title,
//                totalAmount,
//                currentAmount,
//                description,
//                goalDate
//        );
//        return goalService.updateGoal(uuid, goalCreateRequestDto);
//    }
//
//
//}
