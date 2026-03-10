package com.codevithkarthik.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codevithkarthik.DTO.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseStructure<String>> handleBadCredentials(
            BadCredentialsException ex){

        ResponseStructure<String> rs = new ResponseStructure<>();

        rs.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        rs.setMessage("Invalid Email or Password");
        rs.setData(null);

        return new ResponseEntity<>(rs,HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidation(
	        MethodArgumentNotValidException ex){

	    Map<String,String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error ->
	        errors.put(error.getField(), error.getDefaultMessage())
	    );

	    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex){
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserExists(UserAlreadyExistsException ex){

	    ResponseStructure<String> rs = new ResponseStructure<>();

	    rs.setStatusCode(HttpStatus.CONFLICT.value());
	    rs.setMessage(ex.getMessage());
	    rs.setData(null);

	    return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(MobileAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleMobileExists(MobileAlreadyExistsException ex) {
		
		 ResponseStructure<String> rs = new ResponseStructure<>();

		    rs.setStatusCode(HttpStatus.CONFLICT.value());
		    rs.setMessage(ex.getMessage());
		    rs.setData(null);
		 
        return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
    }
	
}
