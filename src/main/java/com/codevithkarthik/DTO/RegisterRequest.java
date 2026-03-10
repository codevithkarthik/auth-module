package com.codevithkarthik.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@NotBlank(message = "Username Required")
	private String name;
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
	private String mobile;
	@Email(message = "Invalid Email")
	private String email;
	@Size(min = 8,message = "Password Must be 8 Characters")
	private String password;
	
	public RegisterRequest() {
		super();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number") String getMobile() {
		return mobile;
	}
	public void setMobile(@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number") String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
