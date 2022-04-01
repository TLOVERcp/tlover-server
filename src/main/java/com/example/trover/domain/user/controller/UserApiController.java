package com.example.trover.domain.user.controller;

import com.example.trover.domain.user.dto.LoginRequest;
import com.example.trover.domain.user.dto.LoginResponse;
import com.example.trover.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserApiController {

    private final UserService userService;

    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

}
