package com.xseedai.identityservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xseedai.identityservice.dto.UserDetailsDTO;
import com.xseedai.identityservice.entity.Role;
import com.xseedai.identityservice.entity.UserCredential;
import com.xseedai.identityservice.exception.TokenValidationException;
import com.xseedai.identityservice.mapper.UserMapper;
import com.xseedai.identityservice.repository.UserCredentialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<String> saveUser(UserDetailsDTO user) {
	    String email = user.getEmail();
	    if (userCredentialRepository.findByEmail(email).isPresent()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
	    }

	    // Set the encoded password
	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    try {
	        // Convert DTO to entity using the mapper
	        UserCredential userEntity = modelMapper.map(user, UserCredential.class); // Assuming UserCredential is the entity class
	        // Save the user along with roles
	        userCredentialRepository.save(userEntity);
	        return ResponseEntity.status(HttpStatus.CREATED).body("User added to the system");
	    } catch (Exception e) {
	        // Handle the exception and return a 500 Internal Server Error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save user");
	    }
	}

    
    
    public Optional<UserCredential> getUserById(int userId) {
        return userCredentialRepository.findById(userId);
    }
    
    public List<UserCredential> getAllUser() {
        return userCredentialRepository.findAll();
    }
    public Optional<UserCredential> getUserByIdWithRoles(int userId) {
        return userCredentialRepository.findById(userId);
    }
    
    public ResponseEntity<String> generateToken(String username, List<Integer> roleIds, int userId) {
        try {
            String token = jwtService.generateToken(username, roleIds, userId);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token");
        }
    }
    
    
    public ResponseEntity<String> validateToken(String token) {
        try {
            jwtService.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (TokenValidationException e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to validate token");
        }
    }

    
    public List<Integer> getUserRoleIds(String username) {
    	Optional<UserCredential> userCredentialOptional = (userCredentialRepository.findByEmail(username));

        if (userCredentialOptional.isPresent()) {
            UserCredential userCredential = userCredentialOptional.get();
            // Retrieve all role IDs associated with the user
            return userCredential.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
        }

        throw new RuntimeException("Roles not found for user: " + username);
    }
    
    
    public int getUserIdByUsername(String username) {
        Optional<UserCredential> userCredentialOptional = userCredentialRepository.findByEmail(username);

        if (userCredentialOptional.isPresent()) {
            return userCredentialOptional.get().getId();
        } else {
            // Handle the case where the user with the given username is not found
            // You can throw an exception, return a default user ID, or handle it as per your requirements
            throw new RuntimeException("User not found for username: " + username);
        }
    }
    
    public int getNameByUserId(String name) {
        Optional<UserCredential> userCredentialOptional = userCredentialRepository.findByName(name);

        if (userCredentialOptional.isPresent()) {
            return userCredentialOptional.get().getId();
        } else {
            // Handle the case where the user with the given username is not found
            // You can throw an exception, return a default user ID, or handle it as per your requirements
            throw new RuntimeException("User not found for username: " + name);
        }
    }
    
 

}
