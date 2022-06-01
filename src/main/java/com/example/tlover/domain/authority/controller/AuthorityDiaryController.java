package com.example.tlover.domain.authority.controller;

import com.example.tlover.domain.authority.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority.dto.CheckAuthorityDiaryResponse;
import com.example.tlover.domain.authority.service.AuthorityDiaryService;
import com.example.tlover.domain.authority.dto.SharePlanRequest;
import com.example.tlover.domain.user.controller.UserApiController;
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
    public ResponseEntity<ResponseDto<AuthorityDiaryResponse>> ShareDiary(@PathVariable Long diaryId,
                                                         @Valid @RequestBody SharePlanRequest sharePlanRequest){

        return ResponseEntity.ok(ResponseDto.create("다이어리 작성 권한을 요청했습니다." , authorityDiaryService.sharePlan(diaryId, sharePlanRequest)));

    }

    @ApiOperation(value = "다이어리 공유 요청 수락", notes = "다이어리 공유 요청을 수락합니다.")
    @PostMapping("/accept-diary-plan/{authorityDiaryId}")
    public ResponseEntity<ResponseDto<AuthorityDiaryResponse>> updateAcceptAuthorityDiary(@PathVariable Long authorityDiaryId,
                                                                             HttpServletRequest request) {


        return ResponseEntity.ok(ResponseDto.create("다이어리 작성 권한을 수락했습니다." ,  authorityDiaryService.updateAcceptAuthorityDiary(authorityDiaryId)));
    }

    @ApiOperation(value = "다이어리 공유 요청 거절", notes = "다이어리 공유 요청을 거절합니다.")
    @PostMapping("/reject-authority-diary/{authorityDiaryId}")
    public ResponseEntity<ResponseDto<AuthorityDiaryResponse>> updateRejectAuthorityDiary(@PathVariable Long authorityDiaryId,
                                                                                 HttpServletRequest request) {
        return ResponseEntity.ok(ResponseDto.create("다이어리 작성 권한을 거절했습니다" , authorityDiaryService.updateRejectAuthorityDiary(authorityDiaryId)));
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
        String loginId = jwtService.getLoginId();
        authorityDiaryService.getListHostAuthor(loginId);

        return null;
    }

    @ApiOperation(value = "해당 다이어리의 권한을 확인", notes = "다이어리를 작성/수정을 시도하는 시점에 권한을 확인함")
    @PostMapping("/check-authority-diary/{diaryId}")
    public ResponseEntity<ResponseDto<CheckAuthorityDiaryResponse>> checkAuthorityDiary(@PathVariable Long diaryId) {

        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("해당 다이어리에 대한 권한 확인" ,  authorityDiaryService.checkAuthorityDiary(loginId , diaryId)));

    }









}
