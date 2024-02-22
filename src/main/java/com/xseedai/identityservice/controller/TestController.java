//package com.xseedai.identityservice.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/test")
//public class TestController {
//
//    @GetMapping("/auth/hello-user")
//    @PreAuthorize("hasRole('USER')")
//    public String helloUser() {
//        // Your secured API logic for user
//        return "Welcome User";
//    }
//
//    @GetMapping("/auth/hello-admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String helloAdmin() {
//        // Your secured API logic for admin
//        return "Welcome Admin";
//    }
//}
//
