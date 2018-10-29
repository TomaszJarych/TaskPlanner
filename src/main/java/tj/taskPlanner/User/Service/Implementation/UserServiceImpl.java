package tj.taskPlanner.User.Service.Implementation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tj.taskPlanner.Commons.DtoAndEntityConverter.DomainConverter;
import tj.taskPlanner.User.Repository.UserRepository;
import tj.taskPlanner.User.Service.UserService;
import tj.taskPlanner.User.domain.User;
import tj.taskPlanner.User.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DomainConverter converter;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
			DomainConverter converter) {
		this.userRepository = userRepository;
		this.converter = converter;
	}

	@Override
	public UserDto getById(Long id) {
		return converter.toUserDto(userRepository.getOne(id));
	}

	@Override
	public UserDto saveToDB(UserDto dto) {
		return converter
				.toUserDto(userRepository.save(converter.toUserEntity(dto)));
	}

	@Override
	public Boolean deleteById(Long id) {
		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<UserDto> getAllFromDB() {
		return toUserDtoList(userRepository.findAll());
	}

	private List<UserDto> toUserDtoList(List<User> list) {
		return list.stream().filter(Objects::nonNull).map(converter::toUserDto)
				.collect(Collectors.toList());
	}

}
