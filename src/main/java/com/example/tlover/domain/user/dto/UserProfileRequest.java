package com.example.tlover.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 정보 수정을 위한 요청 객체")
public class UserProfileRequest {

    /** 로그인 ID 수정 불가능
    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{6,18}$", message = "로그인 Id는 6~18글자의 영소문자, 숫자만 가능합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;
    **/

    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "이메일 양식이 맞지 않습니다.")
    @ApiModelProperty(notes = "이메일을 입력해 주세요.")
    private String userEmail;

    @NotBlank(message = "회원의 닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9가-힣]{6,18}$", message = "닉네임은 6~18글자의 영소문자, 숫자, 한글만 가능합니다.")
    @ApiModelProperty(notes = "닉네임을 입력해 주세요.")
    private String userNickName;

    @NotNull(message = "유제 테마가 null 입니다.")
    @ApiModelProperty(notes = "유제 테마가 null 입니다.")
    private List<String> userThemaName;


}