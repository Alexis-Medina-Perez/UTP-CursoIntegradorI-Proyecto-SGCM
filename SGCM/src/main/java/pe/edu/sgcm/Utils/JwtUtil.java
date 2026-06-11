/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.sgcm.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


/**
 *
 * @author alexis
 */

public class JwtUtil {

    // ✅ usar SecretKey (NO Key)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "clave_secreta_clave_secreta_clave_secreta_123".getBytes()
    );

    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    // ✅ generar token
    public static String generateToken(Integer userId, String username) {

        return Jwts.builder()
                .claim("id_usuario", userId)
                .claim("username", username)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    // ✅ leer claims
    public static Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(SECRET_KEY) // ✅ ahora sí funciona
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static Integer getUserId(String token) {
        return getClaims(token).get("id_usuario", Integer.class);
    }

    public static String getUsername(String token) {
        return getClaims(token).get("username", String.class);
    }
}
