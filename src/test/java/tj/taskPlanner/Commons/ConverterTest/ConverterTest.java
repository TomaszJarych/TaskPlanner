package tj.taskPlanner.Commons.ConverterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import tj.taskPlanner.Commons.DtoAndEntityConverter.DomainConverter;
import tj.taskPlanner.Commons.Enum.UserRole;
import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class ConverterTest {

	private DomainConverter converter;

	@Mock
	UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		converter = new DomainConverter(userRepository);
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

}
