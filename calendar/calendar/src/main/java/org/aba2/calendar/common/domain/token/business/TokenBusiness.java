package org.aba2.calendar.common.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.domain.token.converter.TokenConverter;
import org.aba2.calendar.common.domain.token.model.TokenResponse;
import org.aba2.calendar.common.domain.token.service.TokenService;
import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;


    public TokenResponse issueToken(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    var userId = it.getUserId();

                    var accessToken = tokenService.getAccessToken(userId);
                    var refreshToken = tokenService.getRefreshToken(userId);

                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT))
                ;
    }


    public String validationAccessToken(String token) {

        return tokenService.validationToken(token);
    }


}
