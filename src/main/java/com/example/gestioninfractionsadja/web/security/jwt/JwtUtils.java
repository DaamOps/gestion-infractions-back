package com.example.gestioninfractionsadja.web.security.jwt;

import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.model.User;
import com.example.gestioninfractionsadja.web.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final JwtConfig jwtConfig;
    private UserDao userDao;

    public JwtUtils(JwtConfig jwtConfig, UserDao userDao) {
        this.jwtConfig = jwtConfig;
        this.userDao = userDao;
    }

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDao.findById(userPrincipal.getId()).get();
        user.setAuthenticated(false);

        return generateJwtTokenFromUser(user);
    }

    public String generateJwtTokenFromUser(User user) {
        if (user.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Vous êtes déjà connecté");
        }


        String newAccessToken = Jwts.builder()
                .setSubject((user.getUsername()))
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole().getLibelle())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();

        // Blacklist old accessToken if exist
        blacklistAccesstoken(user.getCurrentAccessToken());

        user.setAuthenticated(true);
        user.setCurrentAccessToken(newAccessToken);
        userDao.save(user);

        return newAccessToken;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public void blacklistAccesstoken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }
    }
}
