package com.example.tlover.domain.plan.dto;
import com.google.errorprone.annotations.FormatString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-4]):(0[0-9]|[0-5][0-9]):(0[0-9]|[0-5][0-9])$", message = "날짜 포멧 확인")
    private String planStartDate;

    @NotNull(message = "여행 마지막 날짜를 입력해주세요.")
    @ApiModelProperty(notes = "여행 마지막 날짜를 입력해 주세요.")
    @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-4]):(0[0-9]|[0-5][0-9]):(0[0-9]|[0-5][0-9])$", message = "날짜 포멧 확인")
    private String planEndDate;

    @NotNull(message = "여행 지역을 입력해주세요.")
    @ApiModelProperty(notes = "여행 지역을 입력해 주세요.")
    private String[] regionName;

    @ApiModelProperty(notes = "여행 경비를 입력 주세요.")
    private Long expense;
    }


