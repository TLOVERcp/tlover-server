package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryApiController {

    private final DiaryService diaryService;

    /**
     * 뷰가 정확하게 나오지 않아서 그냥 다 조회해버렸습니다 ^~^
     * 서비스 부분에 List로 리턴받아야해서 했는데 이게 맞는지 잘 모르겟네요
     * @param request
     * @return
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 조회",notes = "다이어를 조호합니다.")
    @GetMapping("/inquiry")
    public ResponseEntity<List<DiaryInquiryResponse>> getDiary(HttpServletRequest request){
        List<DiaryInquiryResponse> diaryInquiryResponse = diaryService.getDiary();
        return ResponseEntity.ok(diaryInquiryResponse);
    }
}
