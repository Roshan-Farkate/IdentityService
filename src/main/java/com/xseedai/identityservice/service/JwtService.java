package com.xseedai.identityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtService {

	//later store it in environment varibles or outside configurations 
	//this is the key used to validated jwt tokens 
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String userName, List<Integer>  roleIds, int userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleIds);
//        claims.put("userId", userId); 
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims) //currently empty list 
                .setSubject(userName) //Set the main thing about the user you want to remember (the userName) in the token.
                
                .setIssuedAt(new Date(System.currentTimeMillis())) //Stamp the current time as the moment this token is created.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) //Decide when this token will expire. In this case, it's set to 30 minutes from now.
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();  //Put a secret signature on the token using a special key (getSignKey()). It's like sealing an envelope with a unique mark only you can create.
    } //enumeration constant that represents the HMAC SHA-256  algorithm used for signing JWTs (JSON Web Tokens)

    


	private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
