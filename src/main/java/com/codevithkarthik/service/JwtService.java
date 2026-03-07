package com.codevithkarthik.service;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private Key getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}	
	
	public String generateToken(String username) {
		return Jwts.builder()
		.setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
		.signWith(getKey(),SignatureAlgorithm.HS256)
		.compact();	
	}

	/*
	 * public String validateToken(String token) {
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(getKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
		
		return claims.getSubject();
	}
	 */

	public String extractUserName(String token) {
		// extract the username(mail) from jwt token
        return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	     final Claims claims = extractAllClaims(token);
	     return claimResolver.apply(claims);
	 }

	 private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	        		.setSigningKey(getKey())
	        		.build()
	        		.parseClaimsJws(token)
	        		.getBody();
	  }


	public boolean validateToken(String token, UserDetails userDetails) {
		 final String userName = extractUserName(token);
	     return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	

}


//Another Way of Generating Token, But key changes every time when Obj creates
	/* public JwtService() {
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=keyGen.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {	
			e.printStackTrace();
		}
	} */
	
	/*private Key getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}*/
