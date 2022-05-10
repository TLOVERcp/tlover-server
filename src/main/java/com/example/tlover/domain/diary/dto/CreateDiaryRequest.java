package com.example.tlover.domain.diary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 작성을 위한 요청 객체")
public class CreateDiaryRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    @ApiModelProperty(notes = "다이어리의 제목을 입력해 주세요.")
    private String diaryTitle;

    @ApiModelProperty(notes = "여행 내용을 입력해 주세요.")
    private String diaryContext;

//     @NotNull(message = "여행시에 찍었던 사진들")
    @ApiModelProperty(notes = "여행시에 찍었던 사진들")
    private List<MultipartFile> diaryImages;

    @NotNull(message = "여행 시작 날짜를 입력해주세요.")
    @ApiModelProperty(notes = "여행 시작 날짜를 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime diaryStartDate;

    @NotNull(message = "여행 마지막 날짜를 입력해주세요.")
    @ApiModelProperty(notes = "여행 마지막 날짜를 입력해 주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime diaryEndDate;

    @NotNull(message = "여행 지역을 입력해주세요.")
    @ApiModelProperty(notes = "여행 지역을  입력해 주세요.")
    private String[] regionName;

    @NotNull
    @ApiModelProperty(notes = "여행 테마를 입력해주세요.")
    private String[] themaName;

    @ApiModelProperty(notes = "계획의 Id를 입력해주세요.")
    private Long planId;

    @Min(value = 1 , message = "1이상의 수를 입력해주세요.")
    @ApiModelProperty(notes = "몇일차인지 입력해주세요")
    private int diaryDay;

    @Min(value = 0 , message = "1이상의 수를 입력해주세요.")
    @ApiModelProperty(notes = "여행의 총경비를 입력해주세요.")
    private int totalCost;

}
