package tj.taskPlanner.Commons.ResultWrapper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Result {

	private static final String OK_CODE = "200";
	private static final String OK_MESSAGE = "OK";

	private String message;
	private String code;
	private Object data;
	private String error;

	public static Result ok(Object data) {
		return new Result(OK_MESSAGE, OK_CODE, data, null);
	}

	public static Result ok(String message) {
		return new Result(message, OK_CODE, null, null);
	}

	public static Result error(String message, String code) {
		return new Result(message, code, null, null);
	}

	public static Result error(String code) {
		return new Result(null, code, null, null);
	}

}
