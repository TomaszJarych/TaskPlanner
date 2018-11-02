package tj.taskPlanner.Project.Service.Implementation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import tj.taskPlanner.Commons.DtoAndEntityConverter.DomainConverter;
import tj.taskPlanner.Project.Repository.ProjectRepository;
import tj.taskPlanner.Project.Service.ProjectService;
import tj.taskPlanner.Project.domain.Project;
import tj.taskPlanner.Project.dto.ProjectDto;

@Service
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final DomainConverter converter;
	public ProjectServiceImpl(ProjectRepository projectRepository,
			DomainConverter converter) {
		this.projectRepository = projectRepository;
		this.converter = converter;
	}

	@Override
	public ProjectDto getById(Long id) throws EntityNotFoundException {
		return converter.toProjectDto(projectRepository.getOne(id));
	}

	@Override
	public ProjectDto saveToDB(ProjectDto dto) {
		return converter.toProjectDto(
				projectRepository.save(converter.toProjectEntity(dto)));
	}

	@Override
	public Boolean deleteById(Long id) {
		try {
			projectRepository.findById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<ProjectDto> getAllFromDB() {
		return toProjectDtoList(projectRepository.findAll());
	}

	private List<ProjectDto> toProjectDtoList(List<Project> list) {
		return list.stream().filter(Objects::nonNull)
				.map(converter::toProjectDto).collect(Collectors.toList());
	}

}
