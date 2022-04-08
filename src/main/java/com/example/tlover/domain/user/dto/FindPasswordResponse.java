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
public class FindPasswordResponse {

    private String message;


    public static FindPasswordResponse from(User user) {
        return FindPasswordResponse.builder()
                .build();
    }

}
