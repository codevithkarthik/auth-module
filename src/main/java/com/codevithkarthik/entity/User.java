package com.codevithkarthik.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private Long mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;//To Track Audit Logs

    public User() {
    	
    }


    public Long getId() {
    	return id;
    }

    public String getName() { 
    	return name; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }

    public Long getMobile() { 
    	return mobile; 
    }
    public void setMobile(Long mobile) { 
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

    public User(String name, Long mobile, String email, String password) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
	}
    

	public User(Long id, String name, Long mobile, String email, String password, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
	}


	public LocalDateTime getCreatedAt() { 
    	return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
    	this.createdAt = createdAt; 
    }
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
