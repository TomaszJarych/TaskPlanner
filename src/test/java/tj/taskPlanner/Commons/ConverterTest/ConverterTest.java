package tj.taskPlanner.Commons.ConverterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

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
import tj.taskPlanner.Task.Repository.TaskRepository;
import tj.taskPlanner.Task.domain.Task;
import tj.taskPlanner.Task.dto.TaskDto;
import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class ConverterTest {

	private DomainConverter converter;

	@Mock
	UserRepository userRepository;

	@Mock
	TaskRepository taskRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		converter = new DomainConverter(userRepository, taskRepository);
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
		
		//when
		TaskDto actual = converter.toTaskDto(task);
		
		//then
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getCreated(), actual.getCreated());
		assertEquals(expected.getRealization(), actual.getRealization());
		assertEquals(expected.getPriority(), actual.getPriority());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
		assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail());
		assertEquals(expected.getOwner().getName(), actual.getOwner().getName());
		assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin());
		assertEquals(expected.getOwner().getUserRole(), actual.getOwner().getUserRole());
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
		
		//when
		Task actual = converter.toTaskEntity(dto);
		
		//then
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getRealization(), actual.getRealization());
		assertEquals(expected.getPriority(), actual.getPriority());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
		assertEquals(expected.getOwner().getEmail(), actual.getOwner().getEmail());
		assertEquals(expected.getOwner().getName(), actual.getOwner().getName());
		assertEquals(expected.getOwner().getLogin(), actual.getOwner().getLogin());
		assertEquals(expected.getOwner().getUserRole(), actual.getOwner().getUserRole());
	}

}
