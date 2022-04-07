package com.example.tlover.domain.plan.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "계획 작성을 위한 요청 객체")
public class CreatePlanRequest {

    @NotBlank(message = "계획 제목을 입력해주세요.")
    @ApiModelProperty(notes = "계획 제목을 입력해 주세요.")
    private String planTitle;

    @NotBlank(message = "계획 내용을 입력해주세요.")
    @ApiModelProperty(notes = "계획 내용을 입력해 주세요.")
    private String planContext;

    @NotNull(message = "여행 시작 날짜를 입력해주세요.")
    @ApiModelProperty(notes = "여행 시작 날짜를 입력해 주세요.")
    private LocalDateTime planStartDate;

    @NotNull(message = "여행 마지막 날짜를 입력해주세요.")
    @ApiModelProperty(notes = "여행 마지막 날짜를 입력해 주세요.")
    private LocalDateTime planEndDate;

    @NotNull(message = "여행 지역을 입력해주세요.")
    @ApiModelProperty(notes = "여행 지역을  입력해 주세요.")
    private String[] regionName;

    }


