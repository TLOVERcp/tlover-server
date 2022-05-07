package com.example.tlover.domain.diary_connect.controller;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary_connect.constant.DiaryConnectionConstants.EDiaryConnectionResponseMessage;
import com.example.tlover.domain.diary_connect.service.DiaryConnectionService;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/diaries/connections")
@RequiredArgsConstructor
@Api(tags = "DiaryConnection API")
public class DiaryConnectionApiController {

    private final DiaryConnectionService diaryConnectionService;

    @ApiOperation(value = "다이어리 상세 조회.", notes = "다이어리를 상세히 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 diaryId로 Diary를 찾을 수 없습니다.(D0004)" , response = NotFoundDiaryException.class),
            @ApiResponse(code = 400, message = "해당 아이디를 찾을 수 없습니다.(U0002)", response = NotFoundUserException.class),    })
    @PostMapping("/{diaryId}/{userId}")
    public ResponseEntity<ResponseDto<DiaryInquiryResponse>> getDiaryDetails(@PathVariable Long diaryId, @PathVariable Long userId) {
        return ResponseEntity.ok(ResponseDto.create(EDiaryConnectionResponseMessage.eGetDiaryDetailsSuccessMessage.getMessage()
                , this.diaryConnectionService.getDiaryDetails(diaryId, userId)));
    }
}
