package com.xseedai.identityservice.dto;

import java.util.Set;

import com.xseedai.identityservice.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetailsDTO {
  //  private int id;
	@NotBlank
    private String name;
	
	@Email
    private String email;
	
	 @Size(min = 8, message = "Password must be at least 8 characters long")
	    @Pattern.List({
	        @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit"),
	        @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter"),
	        @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase letter"),
	        @Pattern(regexp = "(?=.*[@#$%^&+=]).+", message = "Password must contain at least one special character")
	})
    private String password;
	 
	 
	 private Set<Role> roles;
  
}
