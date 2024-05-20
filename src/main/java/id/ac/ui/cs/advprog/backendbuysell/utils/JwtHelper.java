package id.ac.ui.cs.advprog.backendbuysell.utils;

import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;


public class JwtHelper {
    private static final String SECRET_KEY = "645367566B59703373367639792F423F4528482B4D6251655468576D5A713474";

    private static final JwtService jwtService = new JwtService();

    public static Long getUserIdFromToken(String jwtToken) {
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String tokenWithoutBearer = jwtToken.replace("Bearer ", "");
        return getUserIdFromTokenWithoutCheckUserRepository(tokenWithoutBearer);
//        return getUserIdFromTokenWithCheckUserRepository(tokenWithoutBearer);
    }

    private static Long getUserIdFromTokenWithoutCheckUserRepository(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(Decoders.BASE64.decode(SECRET_KEY)) // Set your secret key here
                .build().parseClaimsJws(token).getBody();
        try {
            String username = (String) claims.get("user_id");
            return Long.parseLong(username);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long getUserIdFromTokenWithCheckUserRepository(String token){
        User user = jwtService.extractUser(token);
        if (!jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Invalid token");
        }
        return user.getId().longValue();
    }
}
