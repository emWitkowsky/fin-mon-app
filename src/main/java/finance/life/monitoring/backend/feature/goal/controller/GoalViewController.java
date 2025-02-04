//package finance.life.monitoring.backend.feature.goal.controller;
//
//import finance.life.monitoring.backend.feature.goal.dto.GoalCreateRequestDto;
//import finance.life.monitoring.backend.feature.goal.model.Goal;
//import finance.life.monitoring.backend.feature.goal.service.GoalService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Controller
//public class GoalViewController {
//
//    private final GoalService goalService;
//
//    public GoalViewController(GoalService goalService) {
//        this.goalService = goalService;
//    }
//
//    @GetMapping("/goal")
//    public String home(Model model) {
//        List<Goal> goals = goalService.getAllGoals();
//        model.addAttribute("goals", goals);
//        return "goal";
//    }
//
//    @PostMapping("/goals")
//    public String createGoal(@RequestParam String title,
//                             @RequestParam BigDecimal totalAmount,
//                             @RequestParam BigDecimal currentAmount,
//                             @RequestParam String description,
//                             @RequestParam LocalDate goalDate,
//                             Model model) {
//
//        GoalCreateRequestDto goalCreateRequestDto = new GoalCreateRequestDto(title, totalAmount, currentAmount, description, goalDate);
//        ResponseEntity<Goal> newGoal = goalService.createGoal(goalCreateRequestDto);
//        model.addAttribute("goals", goalService.getAllGoals());
//        return "goal";
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public String deleteGoal(@PathVariable("id") UUID uuid, Model model) {
//        goalService.deleteGoal(uuid);
//        model.addAttribute("goals", goalService.getAllGoals());
//        return "redirect:/api/v0/goal";
//    }
//
//    @GetMapping("/details/{id}")
//    public String goalDetails(@PathVariable("id") UUID uuid, Model model) {
//        goalService.getGoalById(uuid).ifPresent(goal -> model.addAttribute("goal", goal));
//        return "details";
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") UUID uuid, Model model) {
//        Optional<Goal> goalOptional = goalService.getGoalById(uuid);
//        if (goalOptional.isPresent()) {
//            model.addAttribute("goal", goalOptional.get());
//            return "edit";
//        } else {
////            model.addAttribute("errorMessage", "Podany goal nie istnieje.");
//            return "goal";
//        }
//    }
//
//    @PostMapping("/edit/{id}")
//    public String saveEditedPerson(@PathVariable("id") UUID uuid, @Valid @ModelAttribute("goal") Goal goal) {
//        goal.setId(uuid);
//
//        GoalCreateRequestDto goalCreateRequestDto = new GoalCreateRequestDto(
//                goal.getTitle(),
//                goal.getTotalAmount(),
//                goal.getCurrentAmount(),
//                goal.getDescription(),
//                goal.getGoalDate()
//        );
//
//        goalService.updateGoal(uuid, goalCreateRequestDto);
//        return "redirect:/api/v0/goal";
//    }
//
//    @PostMapping("/{id}/deposit")
//    public String updateCurrentAmount(@PathVariable("id") UUID uuid,
//                                      @RequestParam BigDecimal amount,
//                                      Model model) {
//        goalService.updateGoalCurrentAmount(uuid, amount);
//
//        model.addAttribute("goals", goalService.getAllGoals());
//
//        return "redirect:/api/v0/goal";
//    }
//}
