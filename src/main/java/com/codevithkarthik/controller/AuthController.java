package com.codevithkarthik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codevithkarthik.DTO.LoginRequest;
import com.codevithkarthik.DTO.RegisterRequest;
import com.codevithkarthik.DTO.ResponseStructure;
import com.codevithkarthik.DTO.UserResponse;
import com.codevithkarthik.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<UserResponse>> register(@RequestBody RegisterRequest request ) {
		 return userService.registerUser(request); 
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<UserResponse>> login(@RequestBody LoginRequest request) {
		return userService.loginUser(request);
	}
}

