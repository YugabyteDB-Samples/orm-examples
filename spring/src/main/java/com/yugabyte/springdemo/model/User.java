package com.yugabyte.springdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long userId;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    private String userEmail;
    
    public void setUserId(Long userId) {
    	this.userId = userId;
    }
    
    public Long getUserId() {
    	return this.userId;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    
    public String getFirstName() {
    	return this.firstName;
    }
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    
    public String getLastName() {
    	return this.lastName;
    }
    
    public void setEmail(String email) {
    	this.userEmail = email;
    }
    
    public String getEmail() {
    	return this.userEmail;
    }
}
