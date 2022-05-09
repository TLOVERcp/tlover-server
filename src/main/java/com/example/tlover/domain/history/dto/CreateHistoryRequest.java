package com.example.tlover.domain.history.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "방문 기록 생성을 위한 요청 객체")
public class CreateHistoryRequest {

    @ApiModelProperty(notes = "다이어리 id를 입력해 주세요.")
    private Long diaryId;

    @ApiModelProperty(notes = "방문한 날짜를 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime date;
}
