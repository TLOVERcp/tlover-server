package com.example.tlover.domain.authority_diary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 작성을 위한 요청 객체")
public class ShareDiaryRequest {

    @NotBlank(message = "공유할 유저 닉네임을 입력해주세요.")
    @ApiModelProperty(notes = "공유할 유저 닉네임을 입력해 주세요.")
    private String userNickName;


}
