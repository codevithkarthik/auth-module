package com.codevithkarthik.service;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.codevithkarthik.DTO.LoginRequest;
import com.codevithkarthik.DTO.RegisterRequest;
import com.codevithkarthik.DTO.ResponseStructure;
import com.codevithkarthik.DTO.UserResponse;
import com.codevithkarthik.entity.User;
import com.codevithkarthik.repositary.UserRepositary;

@Service
public class UserService {	
	@Autowired
	private UserRepositary userRepo;

	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(RegisterRequest request) {
		String name=request.getName();
		Long mobile=request.getMobile();
		String email=request.getEmail();
		String password=request.getPassword();
		String hashPassword=BCrypt.hashpw(password, BCrypt.gensalt());
		User user=new User(name,mobile,email,hashPassword);
		User savedUser=userRepo.save(user);
		
		ResponseStructure<UserResponse> rs=new ResponseStructure<UserResponse>();
		UserResponse userResp=new UserResponse();
		userResp.setId(savedUser.getId());
		userResp.setName(savedUser.getName());
		userResp.setEmail(savedUser.getEmail());
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("User Registered Successfully");
		rs.setData(userResp);
		return new ResponseEntity<ResponseStructure<UserResponse>>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<UserResponse>> loginUser(LoginRequest request) {
		String email=request.getEmail();
		String password=request.getPassword();
		Optional <User> userOpt=userRepo.findByEmail(email);
		ResponseStructure<UserResponse> rs=new ResponseStructure<UserResponse>();
		if(userOpt.isEmpty()) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("User Not Found");
			rs.setData(null);
			return new ResponseEntity<ResponseStructure<UserResponse>>(rs, HttpStatus.NOT_FOUND);
		}
		User user=userOpt.get();
		if(BCrypt.checkpw(password,user.getPassword())) {
			UserResponse userResp=new UserResponse();
			userResp.setId(user.getId());
			userResp.setName(user.getName());
			userResp.setEmail(user.getEmail());
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Login Successfull");
			rs.setData(userResp);
			return new ResponseEntity<ResponseStructure<UserResponse>>(rs, HttpStatus.OK);
		}
		rs.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		rs.setMessage("Incorrect Password");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<UserResponse>>(rs, HttpStatus.UNAUTHORIZED);
	}
}
