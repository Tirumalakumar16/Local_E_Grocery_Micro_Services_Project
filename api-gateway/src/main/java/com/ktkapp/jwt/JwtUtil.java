package com.ktkapp.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    public static final String SECRET = "E61684CC76BE55486KTKKTKTKTKTKTKTKTKTKTKTKTK7E67B4E8F2DD";


    public String extractUsername(String token) {
       return extractClaim(token, Claims::getSubject);
    }

   public Date extractExpiration(String token) {
       return extractClaim(token, Claims::getExpiration);
   }
      public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String token) {
        return Jwts
           .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
           .parseClaimsJws(token)
                .getBody();
    }

        public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {

        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }
}
