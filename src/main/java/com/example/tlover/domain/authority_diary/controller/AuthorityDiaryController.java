package com.example.tlover.domain.authority_diary.controller;

import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority_diary.dto.ShareDiaryResponse;
import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.dto.SharePlanResponse;
import com.example.tlover.domain.authority_plan.dto.UpdateAuthorityPlanResponse;
import com.example.tlover.domain.diary.exception.NoSuchElementException;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.dto.ResponseDto;
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
                    .message("다이어리 권한 요청을 성공하였습니다.")
                    .build());
    }

    @ApiOperation(value = "다이어리 공유 요청 수락", notes = "다이어리 공유 요청을 수락합니다.")
    @PostMapping("/accept-diary-plan/{authorityDiaryId}")
    public ResponseEntity<AuthorityDiaryResponse> updateAcceptAuthorityDiary(@PathVariable Long authorityDiaryId,
                                                                             HttpServletRequest request) {

        authorityDiaryService.updateAcceptAuthorityDiary(authorityDiaryId);
        return ResponseEntity.ok(AuthorityDiaryResponse.builder()
                .message("다이어리 공유 요청을 수락하였습니다.")
                .build());
    }

    @ApiOperation(value = "다이어리 공유 요청 거절", notes = "다이어리 공유 요청을 거절합니다.")
    @PostMapping("/reject-authority-diary/{authorityDiaryId}")
    public ResponseEntity<AuthorityDiaryResponse> updateRejectAuthorityDiary(@PathVariable Long authorityDiaryId,
                                                                                 HttpServletRequest request) {
        authorityDiaryService.updateRejectAuthorityDiary(authorityDiaryId);
        return ResponseEntity.ok(AuthorityDiaryResponse.builder()
                .message("다이어리 공유 요청을 거절하였습니다.")
                .build());
    }

    @ApiOperation(value = "다이어리 공유 권한 알림 확인" , notes ="유저별 다이어리 공유 수락 요청들을 확인한다.")
    @PostMapping("/list-request-authority-diary")
    public ResponseEntity<ResponseDto<List<AuthorityDiaryListResponse>>> listRequestAuthorityDiary(){
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create(authorityDiaryService.getListRequestAuthUser(loginId)));
    }

    @ApiOperation(value = "다이어리 공유 상태 확인", notes = "다이어리를 공유를 요청한 권한자의 시점에서 요청 상태를 확인한다.")
    @PostMapping("/list-host-authority-diary")
    public String listHostAuthorityDiary() {
        /**
         *
         */

        String loginId = jwtService.getLoginId();
        authorityDiaryService.getListHostAuthor(loginId);

        return null;
    }









}
