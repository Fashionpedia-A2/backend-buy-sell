package id.ac.ui.cs.advprog.backendbuysell.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtHelper {
    private static final String SECRET_KEY = "645367566B59703373367639792F423F4528482B4D6251655468576D5A713474";

    public static Long getUserIdFromToken(String jwtToken) {
        String tokenWithoutBearer = jwtToken.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()) // Set your secret key here
                .build().parseClaimsJws(tokenWithoutBearer).getBody();
        try {
            return Long.parseLong((String) claims.get("userId"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
