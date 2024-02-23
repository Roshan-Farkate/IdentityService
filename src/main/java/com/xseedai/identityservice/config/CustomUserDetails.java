package com.xseedai.identityservice.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.xseedai.identityservice.entity.UserCredential;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails { //UserDetails interface is in springsecurity whhich has all below methods

    private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    public CustomUserDetails(UserCredential userCredential) {
        this.username = userCredential.getEmail();//userCredential.getName();
        this.password = userCredential.getPassword();
        this.authorities = userCredential.getRoles()
                .stream()
                .map(Role -> new SimpleGrantedAuthority(Role.getRole()))
                .collect(Collectors.toSet()); // Add this line to terminate the stream
}
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
