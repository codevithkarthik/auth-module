package com.codevithkarthik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codevithkarthik.DTO.LoginRequest;
import com.codevithkarthik.DTO.LoginResponse;
import com.codevithkarthik.DTO.RegisterRequest;
import com.codevithkarthik.DTO.ResponseStructure;
import com.codevithkarthik.DTO.UserPrincipal;
import com.codevithkarthik.DTO.UserResponse;
import com.codevithkarthik.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<ResponseStructure<UserResponse>> register(@Valid @RequestBody RegisterRequest request ) {
		 return userService.registerUser(request); 
	}
	
	@PostMapping("/login")
	@Operation(summary = "Login user and generate JWT token")
	public ResponseEntity<ResponseStructure<LoginResponse>> login(@RequestBody LoginRequest request) {
		return userService.loginUser(request);
	}
	
	@GetMapping("/profile")
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<String> getProfile(@AuthenticationPrincipal UserPrincipal user) {
		return ResponseEntity.ok("Welcome Back " + user.getName());
	}
	
	
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	public String adminEndpoint() {
	    return "Hello Admin";
	}
	
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	@SecurityRequirement(name = "bearerAuth")
	public String userEndpoint() {
	    return "Hello User";
	}
}

