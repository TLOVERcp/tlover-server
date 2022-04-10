package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.CreateDiaryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.diary.service.DiaryServiceImpl;
import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.domain.user.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private final UserApiController userApiController;
    private final DiaryService diaryService;

    @ApiOperation(value = "다이어리 작성", notes = "다이어리 작성을 합니다.")
    @PostMapping(value = "/create-diary" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateDiaryResponse> CreatePlan(@ModelAttribute("createDiaryRequest") CreateDiaryRequest createDiaryRequest ,
                HttpServletRequest request) {

//        String loginId = userApiController.getLoginIdFromSession(request);
//
//        Diary diary = diaryService.createDiary(createDiaryRequest, loginId);


            System.out.println("diary = " + createDiaryRequest.getDiaryTitle());
//        return ResponseEntity.ok(CreatePlanResponse.builder()
//                .message("계획 작성을 성공하였습니다.")
//                .build());


        return null;
    }

//    @PostMapping(value = "/test")
//    public void test(@RequestBody(value = "files") List<MultipartFile> files) {
//        System.out.println("files = " + files);
//    }




}
