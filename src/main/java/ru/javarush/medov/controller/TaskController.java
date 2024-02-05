package ru.javarush.medov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.medov.controller.dto.TaskDTO;
import ru.javarush.medov.domain.Task;
import ru.javarush.medov.service.TaskService;
import java.util.List;
import java.util.stream.Stream;
import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getTasks(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page, @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.getAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        model.addAttribute("statuses", taskService.getStatusList());

        int countPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        model.addAttribute("page_count", countPages);

        if (countPages > 1) {
            List<Integer> pages = Stream.iterate(1, x -> x + 1).limit(countPages).toList();
            model.addAttribute("page_numbers", pages);
        }
        return "tasks";
    }


    @PostMapping("/{id}")
    public String editTask(Model model, @PathVariable("id") Integer id, @RequestBody TaskDTO taskDTO) {
        if (isNull(id) || id <= 0) throw new RuntimeException("invalid id");

        taskService.edit(id, taskDTO.getDescription(), taskDTO.getStatus());
        return getTasks(model, 1, 10);
    }

    @PostMapping("/")
    public String addTask(@RequestBody TaskDTO taskDTO) {
        int limit = 10;
        int countPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        taskService.create(taskDTO.getDescription(), taskDTO.getStatus());
        return "tasks";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(Model model, @PathVariable("id") Integer id) {
        if (isNull(id) || id <= 0) throw new RuntimeException("invalid id");

        taskService.delete(id);
        return getTasks(model, 1, 10);
    }

}
