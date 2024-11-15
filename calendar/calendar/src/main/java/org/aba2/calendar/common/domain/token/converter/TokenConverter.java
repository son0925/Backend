package org.aba2.calendar.common.domain.token.converter;


import org.aba2.calendar.common.annotation.Converter;
import org.aba2.calendar.common.domain.token.model.TokenDto;
import org.aba2.calendar.common.domain.token.model.TokenResponse;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Objects;

@Converter
public class TokenConverter {

    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken) {
        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .build();
    }

}
