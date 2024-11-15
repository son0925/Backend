package org.aba2.calendar.common.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.annotation.Business;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.token.business.TokenBusiness;
import org.aba2.calendar.common.domain.token.model.TokenResponse;
import org.aba2.calendar.common.domain.user.converter.UserConverter;
import org.aba2.calendar.common.domain.user.model.User;
import org.aba2.calendar.common.domain.user.model.UserLoginRequest;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.domain.user.model.UserResponse;
import org.aba2.calendar.common.domain.user.service.UserService;
import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.util.Objects;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;

    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    public TokenResponse login(
            Api<UserLoginRequest> userLoginRequest
    ) {
        // Null 인지 확인하기
        if (Objects.isNull(userLoginRequest) || Objects.isNull(userLoginRequest.getBody())) {
            // 들어온 데이터가 없거나 body 데이터가 없으면 에러 발생
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var data = userLoginRequest.getBody();

        var userId = data.getUserId();
        var password = data.getPassword();

        var entity = userService.login(userId, password);

        return tokenBusiness.issueToken(entity);
    }

    public UserResponse register(Api<UserRegisterRequest> userRegisterRequestApi) {

        if (Objects.isNull(userRegisterRequestApi) || Objects.isNull(userRegisterRequestApi.getBody())) {
            // 들어온 데이터가 없거나 body 데이터가 없으면 에러 발생
            throw new ApiException(ErrorCode.NULL_POINT);
        }

        var data = userRegisterRequestApi.getBody();

        var entity = userConverter.toEntity(data);

        var saveEntity = userService.register(entity);

        return userConverter.toResponse(saveEntity);
    }

    public UserResponse me(User user) {

        var userInfo = userService.getUserWithThrow(user.getId());

        return userConverter.toResponse(userInfo);

    }
}
