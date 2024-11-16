package com.javaweb.controllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.javaweb.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.javaweb.model.ErrorResponseDTO;

import customexception.FieldRequiredException;

@ControllerAdvice // Khi nào bị lỗi thì nó vào đấy
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ArithmeticException.class)
	    public ResponseEntity<Object> handleArithmeticException(
	    		ArithmeticException ex, WebRequest request) {  		
    		ErrorResponseDTO errorResponseDTO= new ErrorResponseDTO();
    		errorResponseDTO.setError(ex.getMessage());
    		List<String> details = new ArrayList<>();
    		details.add("So nguyen lam sao chia duoc cho 0");
    		errorResponseDTO.setDetail(details);
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    
    @ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<Object> handleRequiredException(
    		FieldRequiredException ex, WebRequest request) {  		
		ErrorResponseDTO errorResponseDTO= new ErrorResponseDTO();
		errorResponseDTO.setError(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add("Check lai name hoac numberofbasement di boi vi dang bi null");
		errorResponseDTO.setDetail(details);
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_GATEWAY);
    }

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponse<String>> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		return new ResponseEntity<>(new ApiResponse<>(999, "Thieu thong tin ve district", null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<String>> handlerRuntimeException(RuntimeException ex){
		return new ResponseEntity<>(new ApiResponse<>(999, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
