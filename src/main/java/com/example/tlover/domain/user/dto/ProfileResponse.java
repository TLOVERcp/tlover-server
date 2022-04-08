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
public class ProfileResponse {

    private Long userId;
    private String loginId;
    private String password;
    private String userEmail;
    private String userPhoneNum;
    private String userNickName;
    private String profileImg;

    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .loginId(user.getUserLoginId())
                .password(user.getUserPassword())
                .userEmail(user.getUserEmail())
                .userNickName(user.getUserNickName())
                .userPhoneNum(user.getUserPhoneNum())
                .profileImg(user.getUserProfileImg())
                .build();
    }

}
