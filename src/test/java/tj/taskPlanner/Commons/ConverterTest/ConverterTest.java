package tj.taskPlanner.Commons.ConverterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import tj.taskPlanner.Commons.DtoAndEntityConverter.DomainConverter;
import tj.taskPlanner.Commons.Enum.Priority;
import tj.taskPlanner.Commons.Enum.TaskStatus;
import tj.taskPlanner.Commons.Enum.UserRole;
import tj.taskPlanner.Project.Repository.ProjectRepository;
import tj.taskPlanner.Project.domain.Project;
import tj.taskPlanner.Project.dto.ProjectDto;
import tj.taskPlanner.Task.Repository.TaskRepository;
import tj.taskPlanner.Task.domain.Task;
import tj.taskPlanner.Task.dto.TaskDto;
import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class ConverterTest {

	private DomainConverter converter;
	private AtomicLong projectId;
	private AtomicLong userId;
	private AtomicLong taskId;

	@Mock
	private UserRepository userRepository;

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private ProjectRepository projectRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		converter = new DomainConverter(userRepository, taskRepository,
				projectRepository);
		projectId = new AtomicLong(1);
		userId = new AtomicLong(1);
		taskId = new AtomicLong(1);

	}

	@Test
	public void converterToUserDtoTest() {
		// given
		Long id = 1L;
		String login = "testLogin";
		String name = "testName";
		String email = "testEmail@test.pl";
		UserRole userRole = UserRole.ADMIN;

		User user = new User();
		user.setId(id);
		user.setLogin(login);
		user.setName(name);
		user.setEmail(email);
		user.setUserRole(userRole);

		UserDto expected = new UserDto();
		expected.setId(id);
		expected.setLogin(login);
		expected.setName(name);
		expected.setEmail(email);
		expected.setUserRole(userRole);

		// when
		UserDto actual = converter.toUserDto(user);

		// then
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getLogin(), actual.getLogin());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getUserRole(), actual.getUserRole());
	}
	@Test
	public void converterToUserEntityTest() {
		// given
		Long id = 1L;
		String login = "testLogin";
		String name = "testName";
		String email = "testEmail@test.pl";
		UserRole userRole = UserRole.ADMIN;

		String password = "password";

		UserDto givenUserDto = new UserDto();
		givenUserDto.setId(id);
		givenUserDto.setLogin(login);
		givenUserDto.setName(name);
		givenUserDto.setEmail(email);
		givenUserDto.setUserRole(userRole);
		givenUserDto.setPassword(password);

		User expected = new User();
		expected.setId(id);
		expected.setLogin(login);
		expected.setName(name);
		expected.setEmail(email);
		expected.setUserRole(userRole);
		expected.setPassword(password);

		User userStub = new User();
		userStub.setId(id);
		userStub.setLogin(login);
		userStub.setName(name);
		userStub.setEmail(email);
		userStub.setUserRole(userRole);
		userStub.setPassword(password);

		Mockito.when(userRepository.getOne(id)).thenReturn(userStub);

		// when
		User actual = converter.toUserEntity(givenUserDto);

		// REFLEKSJA!
		// Method method = ReflectionUtils.findMethod(GameServiceImpl.class,
		// "toDto", Game.class);
		// method.setAccessible(true);

		// then
		assertNotNull(actual);
		assertEquals(givenUserDto.getId(), actual.getId());
		assertEquals(givenUserDto.getLogin(), actual.getLogin());
		assertEquals(givenUserDto.getName(), actual.getName());
		assertEquals(givenUserDto.getEmail(), actual.getEmail());
		assertEquals(givenUserDto.getUserRole(), actual.getUserRole());
	}

	@Test
	public void converterToTaskDtoTest() {
		// given
		Long userId = 1L;
		String login = "testLogin";
		String userName = "testName";
		String email = "testEmail@test.pl";
		UserRole userRole = UserRole.ADMIN;

		UserDto ownerDto = new UserDto();
		ownerDto.setId(userId);
		ownerDto.setEmail(email);
		ownerDto.setLogin(login);
		ownerDto.setName(userName);
		ownerDto.setUserRole(userRole);

		User userOwnerStub = new User();
		userOwnerStub.setId(userId);
		userOwnerStub.setEmail(email);
		userOwnerStub.setLogin(login);
		userOwnerStub.setName(userName);
		userOwnerStub.setUserRole(userRole);

		LocalDateTime testCreatedDate = LocalDateTime.now();
		Long id = 1L;
		String name = "testTaskName";
		String description = "testTaskDescription";
		Priority priority = Priority.NEW;
		LocalDateTime created = testCreatedDate;
		LocalDateTime realization = testCreatedDate.plusDays(2L);
		UserDto owner = ownerDto;
		TaskStatus status = TaskStatus.NEW;

		Task task = new Task();
		task.setId(id);
		task.setName(name);
		task.setDescription(description);
		task.setPriority(priority);
		task.setCreated(created);
		task.setRealization(realization);
		task.setOwner(userOwnerStub);
		task.setStatus(status);

		TaskDto expected = new TaskDto();
		expected.setId(id);
		expected.setName(name);
		expected.setDescription(description);
		expected.setPriority(priority);
		expected.setCreated(created);
		expected.setRealization(realization);
		expected.setOwner(ownerDto);
		expected.setStatus(status);

		// when
		TaskDto actual = converter.toTaskDto(task);

		// then
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getCreated(), actual.getCreated());
		assertEquals(expected.getRealization(), actual.getRealization());
		assertEquals(expected.getPriority(), actual.getPriority());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
		assertEquals(expected.getOwner().getEmail(),
				actual.getOwner().getEmail());
		assertEquals(expected.getOwner().getName(),
				actual.getOwner().getName());
		assertEquals(expected.getOwner().getLogin(),
				actual.getOwner().getLogin());
		assertEquals(expected.getOwner().getUserRole(),
				actual.getOwner().getUserRole());
	}

	@Test
	public void converterToTaskEntityTest() {
		// given
		Long userId = 1L;
		String login = "testLogin";
		String userName = "testName";
		String email = "testEmail@test.pl";
		UserRole userRole = UserRole.ADMIN;

		UserDto ownerDto = new UserDto();
		ownerDto.setId(userId);
		ownerDto.setEmail(email);
		ownerDto.setLogin(login);
		ownerDto.setName(userName);
		ownerDto.setUserRole(userRole);

		User userOwnerStub = new User();
		userOwnerStub.setId(userId);
		userOwnerStub.setEmail(email);
		userOwnerStub.setLogin(login);
		userOwnerStub.setName(userName);
		userOwnerStub.setUserRole(userRole);

		LocalDateTime testCreatedDate = LocalDateTime.now();
		Long id = null;
		String name = "testTaskName";
		String description = "testTaskDescription";
		Priority priority = Priority.NEW;
		LocalDateTime created = testCreatedDate;
		LocalDateTime realization = testCreatedDate.plusDays(2L);
		UserDto owner = ownerDto;
		TaskStatus status = TaskStatus.NEW;

		TaskDto dto = new TaskDto();
		dto.setId(id);
		dto.setName(name);
		dto.setDescription(description);
		dto.setPriority(priority);
		dto.setRealization(realization);
		dto.setOwner(ownerDto);
		dto.setStatus(status);

		Task expected = new Task();
		expected.setId(id);
		expected.setName(name);
		expected.setDescription(description);
		expected.setPriority(priority);
		expected.setRealization(realization);
		expected.setOwner(userOwnerStub);
		expected.setStatus(status);

		Mockito.when(userRepository.getOne(userId)).thenReturn(userOwnerStub);

		// when
		Task actual = converter.toTaskEntity(dto);

		// then
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getRealization(), actual.getRealization());
		assertEquals(expected.getPriority(), actual.getPriority());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
		assertEquals(expected.getOwner().getEmail(),
				actual.getOwner().getEmail());
		assertEquals(expected.getOwner().getName(),
				actual.getOwner().getName());
		assertEquals(expected.getOwner().getLogin(),
				actual.getOwner().getLogin());
		assertEquals(expected.getOwner().getUserRole(),
				actual.getOwner().getUserRole());
	}

	@Test
	public void converterToProjectsDtoTest() {
		// given
		String projectName = "TestProjectName";
		String projectDescription = "TestProjectDescription";
		LocalDateTime projectCommonCreationTime = LocalDateTime.now();

		String userLogin = "testLogin";
		String userName = "testName";
		String userEmail = "testEmail@test.pl";
		UserRole userRole = UserRole.ADMIN;

		LocalDateTime testCreatedDate = LocalDateTime.now();
		String taskName = "testTaskName";
		String taskDescription = "testTaskDescription";
		Priority taskPriority = Priority.NEW;
		LocalDateTime taskCreated = testCreatedDate;
		LocalDateTime taskRealization = testCreatedDate.plusDays(2L);
		TaskStatus taskStatus = TaskStatus.NEW;

		User user1 = new User();
		user1.setId(userId.get());
		user1.setName(userName);
		user1.setLogin(userLogin);
		user1.setUserRole(userRole);

		UserDto dto1 = new UserDto();
		dto1.setId(userId.get());
		dto1.setName(userName);
		dto1.setLogin(userLogin);
		dto1.setUserRole(userRole);

		User user2 = new User();
		user2.setId(userId.get());
		user2.setName(userName);
		user2.setLogin(userLogin);
		user2.setUserRole(userRole);

		UserDto dto2 = new UserDto();
		dto2.setId(userId.get());
		dto2.setName(userName);
		dto2.setLogin(userLogin);
		dto2.setUserRole(userRole);

		Task task1 = new Task();
		task1.setId(taskId.get());
		task1.setDescription(taskDescription);
		task1.setName(taskName);
		task1.setCreated(taskCreated);
		task1.setPriority(taskPriority);
		task1.setRealization(taskRealization);
		task1.setStatus(taskStatus);
		task1.setOwner(user1);

		TaskDto taskDto1 = new TaskDto();
		taskDto1.setId(taskId.get());
		taskDto1.setDescription(taskDescription);
		taskDto1.setName(taskName);
		taskDto1.setCreated(taskCreated);
		taskDto1.setPriority(taskPriority);
		taskDto1.setRealization(taskRealization);
		taskDto1.setStatus(taskStatus);
		taskDto1.setOwner(dto1);

		Set<User> projectUsers = new HashSet<>();
		projectUsers.add(user1);
		projectUsers.add(user2);

		Set<Task> projectTasks = new HashSet<>();
		projectTasks.add(task1);

		Set<UserDto> dtoProjectsUsers = new HashSet<>();
		dtoProjectsUsers.add(dto1);
		dtoProjectsUsers.add(dto2);

		Set<TaskDto> dtoProjectsTasks = new HashSet<>();
		dtoProjectsTasks.add(taskDto1);

		Project inputProject = new Project();
		inputProject.setId(projectId.get());
		inputProject.setName(projectName);
		inputProject.setDescription(projectDescription);
		inputProject.setCreation(projectCommonCreationTime);
		inputProject.setTeam(projectUsers);
		inputProject.setTasks(projectTasks);

		ProjectDto expected = new ProjectDto();
		expected.setId(projectId.get());
		expected.setName(projectName);
		expected.setDescription(projectDescription);
		expected.setCreation(projectCommonCreationTime);
		expected.setTeam(dtoProjectsUsers);
		expected.setTasks(dtoProjectsTasks);

		// when

		ProjectDto actual = converter.toProjectDto(inputProject);

		// then

		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getCreation(), actual.getCreation());

		assertNotNull(actual.getTeam());
		assertEquals(expected.getTeam().size(), actual.getTeam().size());

		UserDto[] expectedUsersArray = expected.getTeam()
				.toArray(new UserDto[expected.getTeam().size()]);
		UserDto[] actualUsersArray = actual.getTeam()
				.toArray(new UserDto[actual.getTeam().size()]);
		for (int i = 0; i < actualUsersArray.length; i++) {
			assertEquals(expectedUsersArray[i].getId(),
					actualUsersArray[i].getId());
			assertEquals(expectedUsersArray[i].getName(),
					actualUsersArray[i].getName());
			assertEquals(expectedUsersArray[i].getLogin(),
					actualUsersArray[i].getLogin());
			assertEquals(expectedUsersArray[i].getUserRole(),
					actualUsersArray[i].getUserRole());
			assertEquals(expectedUsersArray[i].getEmail(),
					actualUsersArray[i].getEmail());
		}

		assertEquals(expected.getTasks().size(), actual.getTasks().size());

		TaskDto[] expectedTaskArray = expected.getTasks()
				.toArray(new TaskDto[expected.getTasks().size()]);
		TaskDto[] actualTaskArray = actual.getTasks()
				.toArray(new TaskDto[actual.getTasks().size()]);

		for (int i = 0; i < actualTaskArray.length; i++) {
			assertEquals(expectedTaskArray[i].getId(),
					actualTaskArray[i].getId());
			assertEquals(expectedTaskArray[i].getName(),
					actualTaskArray[i].getName());
			assertEquals(expectedTaskArray[i].getCreated(),
					actualTaskArray[i].getCreated());
			assertEquals(expectedTaskArray[i].getDescription(),
					actualTaskArray[i].getDescription());
			assertEquals(expectedTaskArray[i].getRealization(),
					actualTaskArray[i].getRealization());
			assertEquals(expectedTaskArray[i].getPriority(),
					actualTaskArray[i].getPriority());
			assertEquals(expectedTaskArray[i].getStatus(),
					actualTaskArray[i].getStatus());
			assertEquals(expectedTaskArray[i].getOwner().getId(),
					actualTaskArray[i].getOwner().getId());
			assertEquals(expectedTaskArray[i].getOwner().getName(),
					actualTaskArray[i].getOwner().getName());
			assertEquals(expectedTaskArray[i].getOwner().getLogin(),
					actualTaskArray[i].getOwner().getLogin());
			assertEquals(expectedTaskArray[i].getOwner().getEmail(),
					actualTaskArray[i].getOwner().getEmail());
			assertEquals(expectedTaskArray[i].getOwner().getUserRole(),
					actualTaskArray[i].getOwner().getUserRole());

		}
	}

}
