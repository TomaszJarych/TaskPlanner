package tj.taskPlanner.User.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tj.taskPlanner.Commons.Enum.UserRole;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {

	private Long id;

	@NotBlank
	private String login;

	@NotBlank
	private String name;

	@NotBlank
	private String email;

	private String password;

	private UserRole userRole;

	public static UserDto insertEmptyUser() {
		UserDto dto = new UserDto();
		dto.setUserRole(UserRole.GUEST);
		return dto;
	}

}
