package com.catoritech.handler;

import com.catoritech.exceptions.BusinessCanNotAccessContactException;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.ContactForUserNotFoundException;
import com.catoritech.exceptions.ContactInvalidIdException;
import com.catoritech.exceptions.EmptyContactListException;
import com.catoritech.exceptions.IndividualUserCanNotAccessException;
import com.catoritech.exceptions.NoRowsUpdatedException;
import com.catoritech.exceptions.UserAlreadyExistException;
import com.catoritech.exceptions.UserNotFoundException;
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
	@ExceptionHandler(NoRowsUpdatedException.class)
	public ResponseEntity<String> handleNoRowsUpdatedException(NoRowsUpdatedException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ContactForUserNotFoundException.class)
	public ResponseEntity<String> handleContactForUserNotFoundExceptionException(ContactForUserNotFoundException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ContactInvalidIdException.class)
	public ResponseEntity<String> handleContactInvalidIdExceptionException(ContactInvalidIdException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmptyContactListException.class)
	public ResponseEntity<String> handleEmptyContactListExceptionException(EmptyContactListException exception) {
		log.error(CAUGHT_EXCEPTION_MESSAGE, exception);
		String error = exception.getMessage();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundExceptionException(UserNotFoundException exception) {
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
