package com.yugabyte.springdemo.model;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID user_id;
    
    @NotBlank
    private String first_name;
    
    @NotBlank
    private String last_name;
    
    private String user_email;
    
    public void setUserId(UUID userId) {
    	this.user_id = userId;
    }
    
    public UUID getUserId() {
    	return this.user_id;
    }
    
    public void setFirstName(String firstName) {
    	this.first_name = firstName;
    }
    
    public String getFirstName() {
    	return this.first_name;
    }
    
    public void setLastName(String lastName) {
    	this.last_name = lastName;
    }
    
    public String getLastName() {
    	return this.last_name;
    }
    
    public void setEmail(String email) {
    	this.user_email = email;
    }
    
    public String getEmail() {
    	return this.user_email;
    }
}
