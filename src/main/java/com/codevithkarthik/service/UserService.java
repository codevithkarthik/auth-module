package com.codevithkarthik.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.codevithkarthik.DTO.LoginRequest;
import com.codevithkarthik.DTO.LoginResponse;
import com.codevithkarthik.DTO.RegisterRequest;
import com.codevithkarthik.DTO.ResponseStructure;
import com.codevithkarthik.DTO.UserPrincipal;
import com.codevithkarthik.DTO.UserResponse;
import com.codevithkarthik.entity.User;
import com.codevithkarthik.enums.Role;
import com.codevithkarthik.exception.UserAlreadyExistsException;
import com.codevithkarthik.repositary.UserRepositary;

@Service
public class UserService {	
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);
	
	private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder(12);
	
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(RegisterRequest request) {
		
		if(userRepo.existsByEmail(request.getEmail())) {
		    throw new UserAlreadyExistsException("User already exists");
		}
		
		String name=request.getName();
		Long mobile=request.getMobile();
		String email=request.getEmail();
		String password=request.getPassword();
		String hashPassword=passwordEncoder.encode(password);//By Using External Library of jBcrypt (BCrypt.hashpw(password, BCrypt.gensalt()))
		User user=new User(name,mobile,email,hashPassword);
		user.setRole(Role.USER);
		User savedUser=userRepo.save(user);
		logger.info("New user registered: {}", email);
		
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
	
	public ResponseEntity<ResponseStructure<LoginResponse>> loginUser(LoginRequest request) {

	    ResponseStructure<LoginResponse> rs = new ResponseStructure<>();

	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	            )
	    );
	    
	    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
	    logger.info("User logged in successfully: {}", request.getEmail());

	    String token = jwtService.generateToken(userPrincipal.getUsername());

	    UserResponse userResp = new UserResponse();
	    userResp.setId(userPrincipal.getUser().getId());
	    userResp.setName(userPrincipal.getUser().getName());
	    userResp.setEmail(userPrincipal.getUsername());

	    LoginResponse loginResp = new LoginResponse(token, userResp);

	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("Login Successful");
	    rs.setData(loginResp);

	    return new ResponseEntity<>(rs, HttpStatus.OK);
	}
}	
	
	
/*
	 * This is by using try and catch block but above case spring internally handles badcredtialexception 
	 * and serach for exception handler which we provided in globalexceptionhandler
	 * 
	 * 
	 * public ResponseEntity<ResponseStructure<LoginResponse>> loginUser(LoginRequest request) {
	    ResponseStructure<LoginResponse> rs = new ResponseStructure<>();
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getEmail(),
	                        request.getPassword()
	                )
	        );
	        // If authentication successful
	        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

	        // Generate JWT using authenticated user
	        String token = jwtService.generateToken(userPrincipal.getUsername());//getUsername return email from UserPrincipal

	        UserResponse userResp = new UserResponse();
	        userResp.setId(userPrincipal.getUser().getId());
	        userResp.setName(userPrincipal.getUser().getName());
	        userResp.setEmail(userPrincipal.getUsername());

	        LoginResponse loginResp = new LoginResponse(token, userResp);

	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Login Successful");
	        rs.setData(loginResp);

	        return new ResponseEntity<>(rs, HttpStatus.OK);

	    } catch (BadCredentialsException e) {
	        rs.setStatusCode(HttpStatus.UNAUTHORIZED.value());
	        rs.setMessage("Invalid Email or Password");
	        rs.setData(null);
	        return new ResponseEntity<>(rs, HttpStatus.UNAUTHORIZED);
	    }
	}
*/

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* Before Spring Security By Using only Spring boot and JWT
	 * 
	 * 
	 public ResponseEntity<ResponseStructure<LoginResponse>> loginUser(LoginRequest request) {
		String email=request.getEmail();
		String password=request.getPassword();
		
		//Fetching User by Using email through UserReopsitory
		Optional <User> userOpt=userRepo.findByEmail(email);
		
		//ResponseStructure Object is Created With LoginResponse Generic
		ResponseStructure<LoginResponse> rs=new ResponseStructure<LoginResponse>();
		
		if(userOpt.isEmpty()) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("User Not Found");
			rs.setData(null);
			return new ResponseEntity<ResponseStructure<LoginResponse>>(rs, HttpStatus.NOT_FOUND);
		}
		
		User user=userOpt.get();
		if(BCrypt.checkpw(password,user.getPassword())) {
			//Token Generation
			String token=jwtService.generateToken(user.getEmail());
			//UserResponse Object Created
			UserResponse userResp=new UserResponse();
			userResp.setId(user.getId());
			userResp.setName(user.getName());
			userResp.setEmail(user.getEmail());
			
			//LoginResponse Object Created
			LoginResponse loginResp=new LoginResponse(token, userResp);
			
			//Updating ResponseStructure Values
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Login Successfull");
			rs.setData(loginResp);
			return new ResponseEntity<ResponseStructure<LoginResponse>>(rs, HttpStatus.OK);
		}
		rs.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		rs.setMessage("Incorrect Password");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<LoginResponse>>(rs, HttpStatus.UNAUTHORIZED);
	}
	
	
	 */
	

