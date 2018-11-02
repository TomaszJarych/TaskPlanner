package tj.taskPlanner.Commons.DtoAndEntityConverter;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tj.taskPlanner.Project.Repository.ProjectRepository;
import tj.taskPlanner.Project.domain.Project;
import tj.taskPlanner.Project.dto.ProjectDto;
import tj.taskPlanner.Task.Repository.TaskRepository;
import tj.taskPlanner.Task.domain.Task;
import tj.taskPlanner.Task.dto.TaskDto;
import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@Component
public class DomainConverter {

	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public DomainConverter(UserRepository userRepository,
			TaskRepository taskRepository,
			ProjectRepository projectRepository) {
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
	}

	public UserDto toUserDto(User user) {
		UserDto dto = new UserDto();

		dto.setId(user.getId());
		dto.setLogin(user.getLogin());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setUserRole(user.getUserRole());

		return dto;
	}

	public User toUserEntity(UserDto dto) throws EntityNotFoundException {
		User user;

		if (dto.getId() != null) {
			user = userRepository.getOne(dto.getId());
		} else {
			user = new User();
			if (dto.getPassword() != null && dto.getPassword() != "") {
				user.setPassword(
						BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
			}
		}

		user.setId(dto.getId());
		user.setLogin(dto.getLogin());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setUserRole(dto.getUserRole());

		return user;
	}

	public TaskDto toTaskDto(Task task) {
		TaskDto dto = new TaskDto();

		dto.setId(task.getId());
		dto.setName(task.getName());
		dto.setDescription(task.getDescription());
		dto.setPriority(task.getPriority());
		dto.setStatus(task.getStatus());
		dto.setCreated(task.getCreated());
		dto.setRealization(task.getRealization());
		dto.setOwner(toUserDto(task.getOwner()));

		return dto;

	}

	public Task toTaskEntity(TaskDto dto) {
		Task task;

		if (Objects.nonNull(dto.getId())) {
			task = taskRepository.getOne(dto.getId());
		} else {
			task = new Task();
		}

		task.setId(dto.getId());
		task.setName(dto.getName());
		task.setDescription(dto.getDescription());
		task.setPriority(dto.getPriority());
		task.setStatus(dto.getStatus());
		task.setRealization(dto.getRealization());
		task.setOwner(userRepository.getOne(dto.getOwner().getId()));

		return task;
	}

	public ProjectDto toProjectDto(Project project) {
		ProjectDto dto = new ProjectDto();

		dto.setId(project.getId());
		dto.setName(project.getName());
		dto.setDescription(project.getDescription());
		dto.setCreation(project.getCreation());

		if (Objects.nonNull(project.getTeam())
				&& !project.getTeam().isEmpty()) {
			dto.getTeam().clear();
			dto.setTeam(project.getTeam().stream().filter(Objects::nonNull)
					.map(this::toUserDto).collect(Collectors.toSet()));
		}

		if (Objects.nonNull(project.getTasks())
				&& !project.getTasks().isEmpty()) {
			dto.getTasks().clear();
			dto.setTasks(project.getTasks().stream().filter(Objects::nonNull)
					.map(this::toTaskDto).collect(Collectors.toSet()));

		}

		return dto;
	}

}
