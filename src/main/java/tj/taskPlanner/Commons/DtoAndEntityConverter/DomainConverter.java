package tj.taskPlanner.Commons.DtoAndEntityConverter;

import javax.persistence.EntityNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@Component
public class DomainConverter {

	private final UserRepository userRepository;

	@Autowired
	public DomainConverter(UserRepository userRepository) {
		this.userRepository = userRepository;
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

}
