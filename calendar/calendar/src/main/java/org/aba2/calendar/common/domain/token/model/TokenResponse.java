package org.aba2.calendar.common.domain.token.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TokenResponse {

    private String accessToken;

    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

}
