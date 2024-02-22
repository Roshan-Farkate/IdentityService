package com.xseedai.identityservice.controller;

import com.xseedai.identityservice.dto.AuthRequest;
import com.xseedai.identityservice.dto.UserDetailsDTO;
import com.xseedai.identityservice.entity.Role;
import com.xseedai.identityservice.entity.UserCredential;
import com.xseedai.identityservice.service.AuthService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    //works perfectly fine 
    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserDetailsDTO user) {
        return service.saveUser(user);
    }
    
  //works perfectly fine 
    @GetMapping("/getuser/{userId}")
    public ResponseEntity<UserCredential> getUserDetailsById(@PathVariable int userId) {
        return service. getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
  //works perfectly fine 
    @GetMapping("/getalluser")
    public List<UserCredential> getAllUser () {
        return service.getAllUser();
               
    }

    //fine
    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            // Retrieve the user details from the database
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            // Retrieve the user ID from the user details
            int userId = service.getUserIdByUsername(userDetails.getUsername());
         // Retrieve the role ID from the user's roles
            List<Integer> roleIds = service.getUserRoleIds(userDetails.getUsername());
            System.out.print(roleIds);
            
            return service.generateToken(authRequest.getUsername(),roleIds,userId);
        } else {
            throw new RuntimeException("invalid access");
        }
    }
  

   
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
