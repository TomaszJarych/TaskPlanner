package tj.taskPlanner.Task.Service;

import java.time.LocalDateTime;
import java.util.List;

import tj.taskPlanner.Commons.GenericService.GenericCrudService;
import tj.taskPlanner.Task.dto.TaskDto;

public interface TaskService extends GenericCrudService<TaskDto, Long> {

	List<TaskDto> getAllTaskByOwnerIdOrderByCreatedDesc(Long id);

	List<TaskDto> getAllTaskByRealizationIsBetweenNowAndOtherDateAndOwnerId(
			LocalDateTime second, Long id);

}
