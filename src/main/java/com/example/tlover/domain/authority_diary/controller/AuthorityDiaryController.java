package com.example.tlover.domain.authority_diary.controller;

import com.example.tlover.domain.authority_diary.dto.ShareDiaryResponse;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.dto.SharePlanResponse;
import com.example.tlover.domain.user.controller.UserApiController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/authority-diarys")
@RequiredArgsConstructor
@Api(tags = "AuthorityDiary API")
public class AuthorityDiaryController {

    private final UserApiController userApiController;
    private final AuthorityDiaryService authorityDiaryService;

    @ApiOperation(value = "다이어리 작성 권한 공유", notes = "다이어리 작성 권한을 공유합니다.")
    @PostMapping("/share-diary/{diaryId}")
    public ResponseEntity<ShareDiaryResponse> ShareDiary(@PathVariable Long diaryId,
                                                         @Valid @RequestBody SharePlanRequest sharePlanRequest){
        authorityDiaryService.sharePlan(diaryId, sharePlanRequest);
        return ResponseEntity.ok(ShareDiaryResponse.builder()
                .message("계획 권한 공유를 성공하였습니다.")
                .build());
    }


}
