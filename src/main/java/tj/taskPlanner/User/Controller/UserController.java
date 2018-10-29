package tj.taskPlanner.User.Controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tj.taskPlanner.Commons.Enum.UserRole;
import tj.taskPlanner.Commons.ErrorsUtil.ErrorsUtil;
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
		System.out.println(Result.ok(userService.getAllFromDB()));
		return Result.ok(userService.getAllFromDB());
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result getLoggedUser(@PathVariable("id") Long id) {
		return Result.ok(userService.getById(id));

	}
	@GetMapping(path = "/logout", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result logoutUser(HttpSession httpSession) {
		httpSession.invalidate();
		return Result.ok("You have been logged out!");

	}

	@PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result addNewUser(@Valid @RequestBody UserDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), "500");
		}
		return Result.ok(userService.saveToDB(dto));
	}

	@PostMapping(path = "/login", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
	public Result logInUser(@RequestBody UserDto dto, HttpSession httpSession) {
		UserDto loggedUser = userService.login(dto);
		if (Objects.isNull(loggedUser)) {
			return Result.error(
					"Invalid password or login. Cannot login given User",
					"500");
		}
		httpSession.setAttribute("loggedUser", loggedUser);
		return Result.ok("You have been logged");
	}

	@PutMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
	public Result editUser(@Valid @RequestBody UserDto dto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.error(ErrorsUtil.errorsToStringFromFieldErrors(
					bindingResult.getFieldErrors()), "500");
		}
		return Result.ok(userService.saveToDB(dto));
	}

	@DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
	public Result deleteUser(@PathVariable("id") Long id,
			HttpSession httpSession) {
		UserDto loggedUser = (UserDto) httpSession.getAttribute("loggedUser");
		if (loggedUser.getUserRole().equals(UserRole.ADMIN)
				&& Objects.nonNull(loggedUser)) {
			return (userService.deleteById(id))
					? Result.ok("User has been deleted")
					: Result.error("ERROR! Cannont delete User", "500");
		}
		return Result.error("Cannont delete User, you have no permission",
				"500");
	}

}
