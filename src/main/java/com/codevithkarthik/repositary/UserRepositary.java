package com.codevithkarthik.repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codevithkarthik.entity.User;

@Repository
public interface UserRepositary extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);//Spring Automatically Creates Query
}
