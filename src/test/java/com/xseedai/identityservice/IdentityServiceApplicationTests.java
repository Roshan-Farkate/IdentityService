package com.xseedai.identityservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.xseedai.identityservice.IdentityServiceApplication;
import com.xseedai.identityservice.controller.AuthController;
import com.xseedai.identityservice.entity.UserCredential;
import com.xseedai.identityservice.service.AuthService;

@SpringBootTest(classes = IdentityServiceApplication.class)
class IdentityServiceApplicationTests {

	@Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;
    
	@Test
	void contextLoads() {
	}

	@Test
    public void testGetUserDetailsById_ExistingUser() {
        // Mocking the service response for an existing user ID
        UserCredential user = new UserCredential();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(authService.getUserById(1)).thenReturn(Optional.of(user));

        // Calling the controller method with an existing user ID
        ResponseEntity<UserCredential> responseEntity = authController.getUserDetailsById(1);

        // Asserting that response status is OK and user details are returned
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetUserDetailsById_NonExistingUser() {
        // Mocking the service response for a non-existing user ID
        when(authService.getUserById(anyInt())).thenReturn(Optional.empty());

        // Calling the controller method with a non-existing user ID
        ResponseEntity<UserCredential> responseEntity = authController.getUserDetailsById(100);

        // Asserting that response status is Not Found
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
