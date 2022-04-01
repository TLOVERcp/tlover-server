package com.example.trover.domain.user.dto;


import com.example.trover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 응답 객체")
public class LoginResponse {

    private Long userId;

    public static LoginResponse from(User user) {
        return LoginResponse.builder()
                .userId(user.getUserId())
                .build();
    }

}
