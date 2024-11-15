package org.aba2.calendar.common.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.domain.token.model.TokenResponse;
import org.aba2.calendar.common.domain.user.business.UserBusiness;
import org.aba2.calendar.common.domain.user.model.UserLoginRequest;
import org.aba2.calendar.common.domain.user.model.UserRegisterRequest;
import org.aba2.calendar.common.domain.user.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    /**
     * 토큰 검사를 하지 않는 컨트롤러
     * 로그인, 회원가입은 토큰을 확인하지 않음
     * 추가할 사항 있다면 주석 달고 추가하자
     */

    // 로그인
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody
            Api<UserLoginRequest> userLoginRequestApi
    ) {

        var response = userBusiness.login(userLoginRequestApi);

        return Api.OK(response);
    }


    // 회원가입
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody
            Api<UserRegisterRequest> userRegisterRequestApi
    ) {

        var response = userBusiness.register(userRegisterRequestApi);

        return Api.OK(response);

    }


}
