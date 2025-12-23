package com.ganeshtech.taskproject.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ganeshtech.taskproject.exception.APIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
//	 private String privateKeyBase64="BASE64_ENCODED_EC_PRIVATE_KEY";
//
//	public String generateToken(Authentication authentication) {
//
//		String email = authentication.getName();
//		Date currentDate = new Date();
//		Date expireDate = new Date(currentDate.getTime() + 3600000);
//
//		String token = Jwts.builder().setSubject(email).setIssuedAt(currentDate).setExpiration(expireDate)
//				.signWith(SignatureAlgorithm.HS256, getPrivateKey()).compact();
//		return token;
//
//	}
//
//	private PrivateKey getPrivateKey() {
//	    try {
//	        byte[] keyBytes = Base64.getDecoder().decode(privateKeyBase64);
//	        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//	        return KeyFactory.getInstance("EC").generatePrivate(spec);
//	    } catch (Exception e) {
//	        throw new RuntimeException("Invalid EC private key", e);
//	    }
//	}
//
//	public String getEmailFromToken(String token) {
//		Claims claims = Jwts.parser().setSigningKey("JWTSecretKey").parseClaimsJws(token).getBody();
//
//		return claims.getSubject();
//	}
//
//	public boolean validateToken(String token) {
//		try {
//			Jwts.parser().setSigningKey("JWTSecretKey").parseClaimsJws(token).getBody();
//		} catch (Exception e) {
//			throw new APIException("Token issue : " + e.getMessage());
//		}
//		return false;
//	}
//	

	private static final String SECRET =
            "my-super-strong-secret-key-should-be-at-least-32-characters";

    public String generateToken(Authentication authentication) {

        String email = authentication.getName();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token);
        return true;
    }
	}


