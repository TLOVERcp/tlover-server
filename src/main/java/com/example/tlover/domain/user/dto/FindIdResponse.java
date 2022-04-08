package com.example.tlover.domain.user.dto;



import com.example.tlover.domain.user.entity.User;
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
public class FindIdResponse {

    private String loginId;
    private String message;


    public static FindIdResponse from(User user) {
        return FindIdResponse.builder()
                .loginId(user.getUserLoginId())
                .build();
    }

}
