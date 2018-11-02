package tj.taskPlanner.Task.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.taskPlanner.Commons.Enum.UserRole;
import tj.taskPlanner.Commons.ResultWrapper.Result;
import tj.taskPlanner.Task.Service.TaskService;
import tj.taskPlanner.Task.dto.TaskDto;
import tj.taskPlanner.User.dto.UserDto;

@RestController
@RequestMapping(path = "/task")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping(path = "/allTaskByUserId/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getAllTasksByUserId(@PathVariable("id") Long id) {
		return Result.ok(taskService.getAllTaskByOwnerIdOrderByCreatedDesc(id));
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getTaskById(@PathVariable("id") Long id) {
		return Result.ok(taskService.getById(id));
	}

	@GetMapping(path = "/getTaskForNextWeek/{id}")
	public Result getTasksByUserIdForNextSevenDays(
			@PathVariable("id") Long id) {
		return Result.ok(taskService
				.getAllTaskByRealizationIsBetweenNowAndOtherDateAndOwnerId(
						LocalDateTime.now().plusDays(7), id));
	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result saveNewTask(@Valid @RequestBody TaskDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error("Task has errors", "500");
		}
		return Result.ok(taskService.saveToDB(dto));
	}

	@PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result editTask(@Valid @RequestBody TaskDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error("Task has errors", "500");
		}
		return Result.ok(taskService.saveToDB(dto));
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteTask(@PathVariable("id") Long id,
			HttpSession httpSession) {
		UserDto loggedUser = (UserDto) httpSession.getAttribute("loggedUser");
		if (loggedUser == null) {
			return Result.error("Cannot delete Task. Please log in!", "500");
		}
		TaskDto dto = taskService.getById(id);
		if (dto.getOwner().getId() == loggedUser.getId()
				|| loggedUser.getUserRole() == UserRole.ADMIN) {
			return (taskService.deleteById(id))
					? Result.ok("Task has been deleted")
					: Result.error("Cannot delete Task", "500");
		}

		return Result.error("Cannot delete Task", "500");

	}

}
