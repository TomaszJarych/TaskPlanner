package tj.taskPlanner.Project.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.taskPlanner.Task.dto.TaskDto;
import tj.taskPlanner.User.dto.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

	private Long id;

	private String name;

	private String description;

	private LocalDateTime creation;

	private Set<UserDto> team = new HashSet();

	private Set<TaskDto> tasks = new HashSet();

}
