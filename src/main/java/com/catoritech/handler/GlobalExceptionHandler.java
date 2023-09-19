package com.catoritech.handler;

import com.catoritech.exceptions.BusinessCanNotAccessContactException;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.IndividualUserCanNotAccessException;
import com.catoritech.exceptions.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.catoritech.constants.LoggerAndExceptionConstants.CAUGHT_EXCEPTION_MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<String> handleAlreadyExistUserException(UserAlreadyExistException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ContactAlreadyExistException.class)
	public ResponseEntity<String> handleAlreadyExistContactException(ContactAlreadyExistException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(BusinessCanNotAccessContactException.class)
	public ResponseEntity<String> handleBusinessCanNotAccessContactException(BusinessCanNotAccessContactException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IndividualUserCanNotAccessException.class)
	public ResponseEntity<String> handleIndividualCanNotAccessContactException(IndividualUserCanNotAccessException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	private Map<String, List<String>> formatErrorsResponse(String... errors) {
		return formatErrorsResponse(Arrays.stream(errors).collect(Collectors.toList()));
	}

	private Map<String, List<String>> formatErrorsResponse(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>(20);
		errorResponse.put("Errors", errors);
		return errorResponse;
	}
}
