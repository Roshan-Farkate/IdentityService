package com.xseedai.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xseedai.identityservice.service.ResetPasswordService;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {
	@Autowired
	private ResetPasswordService resetPasswordService;

	@PostMapping("/request")
	public ResponseEntity<?> requestReset(@RequestParam String email) {
		return resetPasswordService.generateResetToken(email);

	}

	@GetMapping("/validate/{token}")
	public ResponseEntity<?> validateToken(@PathVariable String token) {
		return resetPasswordService.isResetTokenValid(token);

	}

	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
		return resetPasswordService.resetPassword(token, newPassword);

	}
}
