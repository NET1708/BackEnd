package com.swd391.backend.service;

import com.swd391.backend.entity.Role;
import com.swd391.backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private static final String SECRET_KEY = "M4NAYThsSbbtJw0bfPPXcxttaDBlgird";
    @Autowired
    private UserService userService;

    //JWT SecurityScheme
    public SecurityScheme jwtSecuritySchema() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    //Create JWT base on username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        User user = userService.findByUsername(username);

        boolean isAdmin = false;
        boolean isParent = false;
        boolean isTeacher = false;
        boolean isStudent = false;
        boolean isStaff = false;
        if (user != null && user.getRoles().size() > 0) {
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                if(role.getRoleName().equals("ADMIN")) {
                    isAdmin = true;
                }
                if(role.getRoleName().equals("PARENT")) {
                    isParent = true;
                }
                if(role.getRoleName().equals("TEACHER")) {
                    isTeacher = true;
                }
                if(role.getRoleName().equals("STUDENT")) {
                    isStudent = true;
                }
                if(role.getRoleName().equals("STAFF")) {
                    isStaff = true;
                }
                claims.put("isAdmin", isAdmin);
                claims.put("isParent", isParent);
                claims.put("isTeacher", isTeacher);
                claims.put("isStudent", isStudent);
                claims.put("isStaff", isStaff);
            }
        }
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, getSecretKey()).compact();
    }

    //Encode the token base64
    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //extract information from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    //extract infor for 1 claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Check time expired
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
