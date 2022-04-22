package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.CreateDiaryResponse;
import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.dto.ModifyDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/diary")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryApiController {

    private final UserApiController userApiController;
    private final DiaryService diaryService;
    private final JwtService jwtService;

    /**
     * 뷰가 정확하게 나오지 않아서 그냥 다 조회해버렸습니다 ^~^
     * @param request
     * @return ResponseEntity<List<DiaryInquiryResponse>>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 조회",notes = "다이어를 조호합니다.")
    @GetMapping("/get-diary")
    public ResponseEntity<List<DiaryInquiryResponse>> getDiary(){
//        String loginId = userApiController.getLoginIdFromSession(request);
        jwtService.getLoginId();
        List<DiaryInquiryResponse> diaryInquiryResponse = diaryService.getDiary();
        return ResponseEntity.ok(diaryInquiryResponse);
    }

    /**
     * 다이어리 작성 api
     * swagger url => [post]  api/v1/plans/create-diary
     * @param createDiaryRequest
     * @param request
     * @return ResponseEntity<CreateDiaryResponse>
     * author => 신동민
     */
    @ApiOperation(value = "다이어리 작성", notes = "다이어리 작성을 합니다.")
    @PostMapping(value = "/create-diary")

    public ResponseEntity<CreateDiaryResponse> CreateDiary(@Valid CreateDiaryRequest createDiaryRequest , HttpServletRequest request) {
        String loginId = jwtService.getLoginId();
        Diary diary = diaryService.createDiary(createDiaryRequest, loginId);
        return ResponseEntity.ok(CreateDiaryResponse.builder()
                .message(diary.getUser().getUserNickName() + "님의 다이어리 작성이 완료 되었습니다").build());

    }


    /**
     * 다이어리 삭제 API
     * swagger url => [post]  api/v1/plans/delete-diary/{diaryId}
     * @param diaryId
     * @param request
     * @return ResponseEntity<CreateDiaryResponse>
     * author => 신동민
     */

    @ApiOperation(value = "다이어리 삭제", notes = "다이어리를 삭제합니다.")
    @PostMapping(value = "/delete-diary/{diaryId}")
    public ResponseEntity<CreateDiaryResponse> DeleteDiary(@PathVariable Long diaryId , HttpServletRequest request) {

        String loginId = jwtService.getLoginId();
        Diary diary = diaryService.deleteDiary(diaryId, loginId);

        return ResponseEntity.ok(CreateDiaryResponse.builder()
                .message(diary.getUser().getUserNickName() + "님이 작성하신 다이어리 삭제가 완료 되었습니다.").build());
    }

    /**
     * 다이어리 수정 API
     * @param modifyDiaryRequest
     * @param request
     * @return ResponseEntity<String>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 수정", notes = "다이어리를 수정합니다.")
    @PostMapping(value = "/modify-diary/{diaryId}")
    public ResponseEntity<String> ModifyDiary(@Valid ModifyDiaryRequest modifyDiaryRequest, HttpServletRequest request){
        String loginId = userApiController.getLoginIdFromSession(request);
        Diary diary = diaryService.modifyDiary(modifyDiaryRequest, loginId);
        return ResponseEntity.ok("다이어리 수정이 완료되었습니다.");
    }

}
