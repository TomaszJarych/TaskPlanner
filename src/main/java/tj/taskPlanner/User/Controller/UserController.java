package tj.taskPlanner.User.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import tj.taskPlanner.User.Service.UserService;
import tj.taskPlanner.User.dto.UserDto;

@RestController
@RequestMapping(path = "/user")
@SessionAttributes({"loggedUser"})
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public List<UserDto> getallUsers(Model model) {
		model.addAttribute("loggedUser", UserDto.insertEmptyUser());
		
		System.out.println(model.asMap());
		return userService.getAllFromDB();
	}

	@ModelAttribute(name = "loggedUser")
	private UserDto getLoggedUserToSession() {
		return UserDto.insertEmptyUser();
	}
}
