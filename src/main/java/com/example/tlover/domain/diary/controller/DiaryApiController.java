package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.CreateDiaryResponse;
import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.user.controller.UserApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryApiController {

    private final UserApiController userApiController;
    private final DiaryService diaryService;

    /**
     * 뷰가 정확하게 나오지 않아서 그냥 다 조회해버렸습니다 ^~^
     * @param request
     * @return
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 조회",notes = "다이어를 조호합니다.")
    @GetMapping("/get-diary")
    public ResponseEntity<List<DiaryInquiryResponse>> getDiary(HttpServletRequest request){
        List<DiaryInquiryResponse> diaryInquiryResponse = diaryService.getDiary();
        return ResponseEntity.ok(diaryInquiryResponse);
    }


    @ApiOperation(value = "다이어리 작성", notes = "다이어리 작성을 합니다.")
    @PostMapping(value = "/create-diary" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CreateDiaryResponse> CreatePlan(@ModelAttribute("createDiaryRequest") CreateDiaryRequest createDiaryRequest ,
                HttpServletRequest request) {

        String loginId = userApiController.getLoginIdFromSession(request);

        Diary diary = diaryService.createDiary(createDiaryRequest, loginId);

        System.out.println("diary = " + createDiaryRequest.getDiaryTitle());
//        return ResponseEntity.ok(CreatePlanResponse.builder()
//                .message("계획 작성을 성공하였습니다.")
//                .build());


        return null;
    }





}
