package tj.taskPlanner.User.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tj.taskPlanner.Commons.Enum.UserRole;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	
	private String login;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private UserRole userRole;
	
	
	public static UserDto insertEmptyUser() {
		UserDto dto = new UserDto();
		dto.setUserRole(UserRole.GUEST);
		return dto;
	}

}
