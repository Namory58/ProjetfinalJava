package org.example.projetfinal.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Calendar;

public class JwtTokenManager {
    /**
     * Méthode qui permet de crypter token user
     * @param 
     * @return
     */
    public static String
    generateToken(int idUser) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        return Jwts.builder()
                .subject(" "+idUser)
                .expiration(calendar.getTime())
                .signWith(secretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public static int getUser(String token) throws Exception {
        Claims claims = Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token).getPayload();
        return Integer.parseInt(claims.getSubject().trim());
    }

    private static SecretKey secretKey(){
        String cleCryptage = "wzUpGa9k4LTV3SHuY8qVrt6wOENkfdes5vLHVc1ex6581Iiq";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(cleCryptage));
    }
}
