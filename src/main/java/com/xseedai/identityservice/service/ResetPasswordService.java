package com.xseedai.identityservice.service;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Optional;
import com.google.common.net.MediaType;
import com.xseedai.identityservice.entity.UserCredential;
import com.xseedai.identityservice.model.EmailModel;
import com.xseedai.identityservice.repository.UserCredentialRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;

@Service
public class ResetPasswordService {
	@Autowired
	private UserCredentialRepository userCredentialRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Configuration config;

	public ResponseEntity<?> generateResetToken(String email) {
        try {
            // Generate a unique token
            String token = UUID.randomUUID().toString();
            System.out.println("Generated Token: " + token);

        
            Optional<UserCredential> userOptional = userCredentialRepository.findByEmail(email);;

            // Alternatively, use the original query
            // Optional<UserCredential> userOptional = userCredentialRepository.findByEmail(email);

            System.out.println("User Optional: " + userOptional);

            if (userOptional.isPresent()) {
                UserCredential user = userOptional.get();
                System.out.println("User found: " + user);

                user.setResetToken(token);
                System.out.println("User before saving: " + user);
                userCredentialRepository.save(user);
                System.out.println("User after saving: " + user);

                // Send reset email with the token
                sendResetEmail( email, token);

                return ResponseEntity.ok("Reset link sent successfully");
            } else {
                System.out.println("User not found");
                return ResponseEntity.badRequest().body("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Please try again or check your email again");
        }
    }

	public ResponseEntity<?> sendResetEmail(String to, String token) throws Exception {
		try {
			String subject = "Password Reset";
			String resetLink = "http://localhost:9898/reset-password/validate/" + token;

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
		

			Template t = config.getTemplate("email_template.ftl");
			EmailModel emailModel = new EmailModel();
			emailModel.setResetLink(resetLink);

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, emailModel.toMap());
			helper.setFrom("atharva.hodage@tcognition.com", "Xseed");
			helper.setTo(to);

			

			message.setSubject(subject);
			helper.setText(html, true);
			javaMailSender.send(message);

			return null;
		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseEntity<?> isResetTokenValid(String token) {
		try {
			UserCredential user = userCredentialRepository.findByResetToken(token);
			if (user != null && !user.isResetTokenExpired()) {
				return ResponseEntity.ok("Token is valid");
			} else {
				return ResponseEntity.badRequest().body("Token is invalid or expired");

			}
		} catch (Exception e) {

			return ResponseEntity.badRequest().body("Exception");
		}
	}

	public ResponseEntity<?> resetPassword(String token, String newPassword) {
		try {
			if (isResetTokenValid(token) != null) {
				UserCredential user = userCredentialRepository.findByResetToken(token);
				// credential.setPassword(passwordEncoder.encode(credential.getPassword()));
				user.setPassword(passwordEncoder.encode(newPassword));
				user.setResetToken(null);
				userCredentialRepository.save(user);
				return ResponseEntity.ok("Password reset successfully");
			} else {
				return ResponseEntity.badRequest().body("Token is invalid or expired");
			}
		} catch (Exception e) {

			return ResponseEntity.badRequest().body("Exception");
		}
	}

}
