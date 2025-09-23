package org.book.bookmanager.User.Services;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.book.bookmanager.User.Model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secretToken;

    private Instant generateExpirationData(){
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(UserModel user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);
            String token = JWT.create()
                    .withIssuer("book-manager-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationData())
                    .sign(algorithm);

            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro while generate token", exception);
        }
    }

    public boolean validationToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);
            JWT.require(algorithm).build().verify(token);
            return true;
        }catch (JWTVerificationException exception){
            return false;
        }
    }

    public String getUserNameOfToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);
            return JWT.require(algorithm).withIssuer("book-manager-api").build().verify(token).getSubject();
        }catch (JWTVerificationException exception){
            return "Token Invalid or Expired";
        }
    }
}
