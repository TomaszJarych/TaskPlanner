package tj.taskPlanner.User.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.taskPlanner.Commons.ResultWrapper.Result;
import tj.taskPlanner.User.Service.UserService;
import tj.taskPlanner.User.dto.UserDto;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getallUsers() {
		return Result.ok(userService.getAllFromDB());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getLoggedUser(@PathVariable("id") Long id) {
		return Result.ok(userService.getById(id));

	}

}
