package org.aba2.calendar.common.domain.token.helper;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.aba2.calendar.common.domain.token.ifs.TokenHelperIfs;
import org.aba2.calendar.common.domain.token.model.TokenDto;
import org.aba2.calendar.common.errorcode.TokenErrorCode;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    // 토큰 발급
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        var expiredLocalDatetime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        var expiredAt = Date.from(
                expiredLocalDatetime.atZone(ZoneId.systemDefault()).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredAt)
                .setClaims(data)
                .compact();

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDatetime)
                .build()
                ;

    }

    // 토큰 발급
    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {

        var expiredLocalTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        var expiredAt = Date.from(expiredLocalTime.atZone(ZoneId.systemDefault()).toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact()
                ;

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalTime)
                .build()
                ;

    }

    // 토큰 검증
    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                ;


        try {
            var result = parser.parseClaimsJws(token);

            return new HashMap<String,Object>(result.getBody());
        } catch (Exception e) {

            if (e instanceof SignatureException) {
                // 토큰이 유효하지 않을 때
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
            else if (e instanceof ExpiredJwtException) {
                // 만료된 토큰
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            } else {
                // 그 외 에러
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }

        }

    }
}
