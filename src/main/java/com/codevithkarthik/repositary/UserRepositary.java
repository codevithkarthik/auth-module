package com.codevithkarthik.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.codevithkarthik.entity.User;

import jakarta.validation.constraints.Pattern;

@Repository
public interface UserRepositary extends JpaRepository<User, Long> {
	
	User findByEmail(String email);//Spring Automatically Creates Query

	boolean existsByEmail(String email);

	boolean existsByMobile(@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number") String mobile);

}
