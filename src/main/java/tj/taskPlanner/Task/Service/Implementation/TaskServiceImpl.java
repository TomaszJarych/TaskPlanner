package tj.taskPlanner.Task.Service.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tj.taskPlanner.Commons.DtoAndEntityConverter.DomainConverter;
import tj.taskPlanner.Task.Repository.TaskRepository;
import tj.taskPlanner.Task.Service.TaskService;
import tj.taskPlanner.Task.domain.Task;
import tj.taskPlanner.Task.dto.TaskDto;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final DomainConverter converter;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository,
			DomainConverter converter) {
		this.taskRepository = taskRepository;
		this.converter = converter;
	}

	@Override
	public TaskDto getById(Long id) throws EntityNotFoundException {
		return converter.toTaskDto(taskRepository.getOne(id));
	}

	@Override
	public TaskDto saveToDB(TaskDto dto) {
		return converter
				.toTaskDto(taskRepository.save(converter.toTaskEntity(dto)));
	}

	@Override
	public Boolean deleteById(Long id) {
		try {
			taskRepository.findById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<TaskDto> getAllFromDB() {
		return toTaskDtoList(taskRepository.findAll());
	}

	@Override
	public List<TaskDto> getAllTaskByOwnerIdOrderByCreatedDesc(Long id) {
		return toTaskDtoList(
				taskRepository.getAllTaskByOwnerIdOrderByCreatedDesc(id));
	}

	@Override
	public List<TaskDto> getAllTaskByRealizationIsBetweenNowAndOtherDateAndOwnerId(
			LocalDateTime second, Long id) {

		return toTaskDtoList(
				taskRepository.getAllTaskByRealizationIsBetweenAndOwnerId(
						LocalDateTime.now(), second, id));
	}

	private List<TaskDto> toTaskDtoList(List<Task> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toTaskDto)
				.collect(Collectors.toList());
	}

}
