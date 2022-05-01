package com.example.tlover.domain.authority_plan.controller;

import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.dto.SharePlanResponse;
import com.example.tlover.domain.authority_plan.dto.UpdateAuthorityPlanResponse;
import com.example.tlover.domain.authority_plan.service.AuthorityPlanService;
import com.example.tlover.domain.plan.exception.NoAuthorityShareException;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/authority-plans")
@RequiredArgsConstructor
@Api(tags = "AuthorityPlan API")
public class AuthorityPlanController {

    @Autowired
 private final AuthorityPlanService authorityPlanService;
    @Autowired
    private final JwtService jwtService;

    /**
     * 계획 권한 공유 API
     * [POST] api/v1/authority-plans/share-plan/:planId
     * @param sharePlanRequest
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 권한 공유", notes = "계획 권한을 공유합니다.")
    @PostMapping("/share-plan/{planId}")
    public ResponseEntity<SharePlanResponse> SharePlan(@PathVariable Long planId,
                                                       @Valid @RequestBody SharePlanRequest sharePlanRequest){
        String loginId = jwtService.getLoginId();
        boolean checkAuthority = authorityPlanService.checkAuthority(loginId, planId);
        if(!checkAuthority) throw new NoAuthorityShareException();
        authorityPlanService.sharePlan(planId, sharePlanRequest);
        return ResponseEntity.ok(SharePlanResponse.builder()
                .message("계획 권한 공유를 성공하였습니다.")
                .build());
    }

    /**
     * 계획 공유 요청 목록 조회 API
     * [GET] api/v1/authority-plans/authority-plan-list
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 공유 요청 목록 조회", notes = "계획 공유 요청 목록을 조회 합니다.")
    @GetMapping("/authority-plan-list")
    public ResponseEntity<List<AuthorityPlanListResponse>> getPlanList() {
        String loginId = jwtService.getLoginId();
        List<AuthorityPlanListResponse> authorityPlanList = authorityPlanService.getSharePlanList(loginId);
        return ResponseEntity.ok(authorityPlanList);
    }

    /**
     * 계획 공유 요청 수락 API
     * [POST] api/v1/authority-plans/accept-authority-plan/:authorityPlanId
     * @param authorityPlanId
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 공유 요청 수락", notes = "계획 공유 요청을 수락합니다.")
    @PostMapping("/accept-authority-plan/{authorityPlanId}")
    public ResponseEntity<UpdateAuthorityPlanResponse> updateAcceptAuthorityPlan(@PathVariable Long authorityPlanId) {
        String loginId = jwtService.getLoginId();
        authorityPlanService.updateAcceptAuthorityPlan(authorityPlanId);
        return ResponseEntity.ok(UpdateAuthorityPlanResponse.builder()
                .message("계획 공유 요청을 수락하였습니다.")
                .build());
    }

    /**
     * 계획 공유 요청 거절 API
     * [POST] api/v1/authority-plans/reject-authority-plan/:authorityPlanId
     * @param authorityPlanId
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 공유 요청 거절", notes = "계획 공유 요청을 거절합니다.")
    @PostMapping("/reject-authority-plan/{authorityPlanId}")
    public ResponseEntity<UpdateAuthorityPlanResponse> updateRejectAuthorityPlan(@PathVariable Long authorityPlanId) {
        String loginId = jwtService.getLoginId();
        authorityPlanService.updateRejectAuthorityPlan(authorityPlanId);
        return ResponseEntity.ok(UpdateAuthorityPlanResponse.builder()
                .message("계획 공유 요청을 거절하였습니다.")
                .build());
    }


}
