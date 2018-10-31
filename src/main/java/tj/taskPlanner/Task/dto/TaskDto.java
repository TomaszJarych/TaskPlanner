package tj.taskPlanner.Task.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.taskPlanner.Commons.Enum.Priority;
import tj.taskPlanner.Commons.Enum.TaskStatus;
import tj.taskPlanner.User.dto.UserDto;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	private Priority priority;

	private LocalDateTime created = LocalDateTime.now();

	@NotNull
	private LocalDateTime realization;

	@NotNull
	private UserDto owner;

	private TaskStatus status = TaskStatus.NEW;

}
