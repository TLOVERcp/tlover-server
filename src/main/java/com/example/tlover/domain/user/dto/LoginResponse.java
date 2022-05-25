package com.example.tlover.domain.user.dto;


import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "로그인을 위한 응답 객체")
public class LoginResponse {

    private String message;
    private String userNickname;
    private List<String> userThemaName;
    private List<String> userRegionName;
    private float userRating;
    private String accessJwt;
    private Long refreshJwtIdx;

    public static LoginResponse from(User user) {
        return LoginResponse.builder()
                .build();
    }

}
