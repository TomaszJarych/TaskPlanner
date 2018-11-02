package tj.taskPlanner.Project.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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
import tj.taskPlanner.Project.Service.ProjectService;
import tj.taskPlanner.Project.dto.ProjectDto;
import tj.taskPlanner.User.dto.UserDto;
@RestController
@RequestMapping(path = "/project", produces = APPLICATION_JSON_UTF8_VALUE)
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping(path = "/all")
	public Result showAllProjects(HttpSession httpSession) {
		UserDto loggedUser = (UserDto) httpSession.getAttribute("loggedUser");

		if (loggedUser == null | loggedUser.getUserRole() != UserRole.ADMIN) {
			return Result.error("You have no privilages to see all projects!",
					"500");
		}

		return Result.ok(projectService.getAllFromDB());
	}

	@GetMapping(path = "/{id}")
	public Result getProjectById(@PathVariable("id") Long id) {
		return Result.ok(projectService.getById(id));

	}

	@PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result saveNewProject(@Valid @RequestBody ProjectDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error("Incomplete data", "500");
		}

		return Result.ok(projectService.saveToDB(dto));
	}

	@PutMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result editProject(@Valid @RequestBody ProjectDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error("Incomplete data", "500");
		}

		return Result.ok(projectService.saveToDB(dto));
	}

	@DeleteMapping("/{id}")
	public Result deleteProject(@PathVariable("id") Long id,
			HttpSession httpSession) {
		UserDto loggedUser = (UserDto) httpSession.getAttribute("loggedUser");
		if (loggedUser == null | loggedUser.getUserRole() != UserRole.ADMIN) {
			return Result.error("You have no privilages to delete  project!",
					"500");
		}

		return (projectService.deleteById(id))
				? Result.ok("Project has been deleted")
				: Result.error("Cannot delete project", "500");
	}

}
