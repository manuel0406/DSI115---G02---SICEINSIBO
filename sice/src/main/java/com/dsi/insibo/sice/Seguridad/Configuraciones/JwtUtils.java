package com.dsi.insibo.sice.Seguridad.Configuraciones;

import java.util.Date;
import java.util.Base64.Decoder;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts.KEY;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtUtils {
    
    @Value("${jwt.secret.key}")
    private String secretKey;          // Firma de autorización (TOKEN)

    @Value("${jwt.time.expiration}")
    private String timeExpiration;     // Tiempo de expiración (1-dia)

    // Método para generar el token de acceso
    @SuppressWarnings("deprecation")
    public String generateAccessToken(String correo, String contrasena) {
        return Jwts.builder()
                .setSubject(correo) // Establece el correo como sujeto del token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration))) // Establece la fecha de expiración del token
                .signWith(getSignaturKey(), SignatureAlgorithm.HS256) // Firma el token con la clave secreta y el algoritmo HS256
                .compact(); // Construye y compacta el token JWT
    }

    // VALIDADOR DE TOKEN DE ACCESO
    @SuppressWarnings("deprecation")
    public boolean isTokenValid(String token)
    {
        try {
            Jwts.parser().setSigningKey(getSignaturKey()).build().parseClaimsJws(token).getBody();       
            return true;
        } catch (Exception e) {
            log.error("TOKEN INVALIDO: ".concat(e.getMessage()));
            return false;

        }
    }

    // OBTENER CLAIMS O INFORMACION DEL TOKE
    public Claims extractClain(String token){
        return Jwts.parser().setSigningKey(getSignaturKey()).build().parseClaimsJws(token).getBody();  
    }

    //OBTENER EL USERNAME
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    // Método para obtener un claim específico del token usando Function
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClain(token);
        return claimsResolver.apply(claims);
    }

    // FIRMA DEL TOKEN
    public Key getSignaturKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
