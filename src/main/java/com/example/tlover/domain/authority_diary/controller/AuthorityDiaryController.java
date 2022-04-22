package com.example.tlover.domain.authority_diary.controller;

import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority_diary.dto.ShareDiaryResponse;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.dto.SharePlanResponse;
import com.example.tlover.domain.authority_plan.dto.UpdateAuthorityPlanResponse;
import com.example.tlover.domain.diary.exception.NoSuchElementException;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.jwt.service.JwtService;
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
    private final UserRepository userRepository;
    private final AuthorityDiaryService authorityDiaryService;
    private final JwtService jwtService;

    @ApiOperation(value = "다이어리 작성 권한 공유", notes = "다이어리 작성 권한을 공유합니다.")
    @PostMapping("/share-diary/{diaryId}")
    public ResponseEntity<ShareDiaryResponse> ShareDiary(@PathVariable Long diaryId,
                                                         @Valid @RequestBody SharePlanRequest sharePlanRequest){

            authorityDiaryService.sharePlan(diaryId ,sharePlanRequest );
            return ResponseEntity.ok(ShareDiaryResponse.builder()
                    .message("계획 권한 공유를 성공하였습니다.")
                    .build());

    }

    @ApiOperation(value = "다이어리 공유 요청 수락", notes = "다이어리 공유 요청을 수락합니다.")
    @PostMapping("/accept-diary-plan/{authorityDiaryId}")
    public ResponseEntity<AuthorityDiaryResponse> updateAcceptAuthorityDiary(@PathVariable Long authorityDiaryId,
                                                                             HttpServletRequest request) {

        authorityDiaryService.updateAcceptAuthorityDiary(authorityDiaryId);
        return ResponseEntity.ok(AuthorityDiaryResponse.builder()
                .message("계획 공유 요청을 수락하였습니다.")
                .build());
    }

//    @ApiOperation(value = "계획 공유 요청 거절", notes = "계획 공유 요청을 거절합니다.")
//    @PostMapping("/reject-authority-plan/{authorityPlanId}")
//    public ResponseEntity<UpdateAuthorityPlanResponse> updateRejectAuthorityPlan(@PathVariable Long authorityPlanId,
//                                                                                 HttpServletRequest request) {
//        String loginId = userApiController.getLoginIdFromSession(request);
//        authorityPlanService.updateRejectAuthorityPlan(authorityPlanId);
//        return ResponseEntity.ok(UpdateAuthorityPlanResponse.builder()
//                .message("계획 공유 요청을 거절하였습니다.")
//                .build());
//    }



}
