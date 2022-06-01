package com.example.tlover.domain.authority.dto;

import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "계획 작성을 위한 요청 객체")
public class SharePlanRequest {

    @NotBlank(message = "공유할 유저 닉네임을 입력해주세요.")
    @ApiModelProperty(notes = "공유할 유저 닉네임을 입력해 주세요.")
    private String userNickName;

}
