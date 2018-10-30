package tj.taskPlanner.Task.dto;

import java.time.LocalDateTime;

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

	private String name;

	private String description;

	private Priority priority;

	private LocalDateTime created = LocalDateTime.now();

	private LocalDateTime realization;

	private UserDto owner;

	private TaskStatus status = TaskStatus.NEW;

}
