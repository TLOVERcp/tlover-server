package com.example.tlover.domain.user.dto;



import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> userThemaName;
    private List<String> userRegionName;
    private float userRating;

    public static ProfileResponse from(User user, List<String> userThemaName, List<String> userRegionName, float userRating) {
        return ProfileResponse.builder()
                .userId(user.getUserId())
                .loginId(user.getUserLoginId())
                .password(user.getUserPassword())
                .userNickName(user.getUserNickName())
                .userPhoneNum(user.getUserPhoneNum())
                .profileImg(user.getUserProfileImg())
                .userThemaName(userThemaName)
                .userRegionName(userRegionName)
                .userRating(userRating)
                .build();
    }

}
