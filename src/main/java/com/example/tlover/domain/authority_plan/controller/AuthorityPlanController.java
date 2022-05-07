package com.example.tlover.domain.authority_plan.controller;

import com.example.tlover.domain.authority_plan.dto.AuthorityPlanListResponse;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.dto.SharePlanResponse;
import com.example.tlover.domain.authority_plan.dto.UpdateAuthorityPlanResponse;
import com.example.tlover.domain.authority_plan.service.AuthorityPlanService;
import com.example.tlover.domain.plan.exception.*;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(code = 400 , message = "해당 아이디를 찾을 수 없습니다.(U0002)" , response = NotFoundUserException.class),
            @ApiResponse(code = 400 , message = "공유가 불가능한 유저(HOST)입니다.(P0010)" , response = DeniedShareHostException.class),
            @ApiResponse(code = 400 , message = "해당 유저에게 이미 계획 공유 요청중입니다.(P0011)" , response = DeniedShareRequestException.class),
            @ApiResponse(code = 400 , message = "이미 공유된 계획입니다. (P0012)" , response = DeniedShareAcceptException.class),
            @ApiResponse(code = 403 , message = "해당 계획에 공유 권한이 없습니다.(P0013)" , response = NoAuthorityShareException.class),
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다. (P0002)" , response = NotFoundPlanException.class)

    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 400 , message = "해당 아이디를 찾을 수 없습니다.(U0002)" , response = NotFoundUserException.class)
    })
    public ResponseEntity<ResponseDto<List<AuthorityPlanListResponse>>> getPlanList() {
        String loginId = jwtService.getLoginId();
        List<AuthorityPlanListResponse> authorityPlanList = authorityPlanService.getSharePlanList(loginId);
        return ResponseEntity.ok(ResponseDto.create("조회 성공",authorityPlanList));
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
    @ApiResponses(value = {
            @ApiResponse(code = 400 , message = "해당 아이디를 찾을 수 없습니다.(U0002)" , response = NotFoundUserException.class),
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다. (P0002)" , response = NotFoundPlanException.class),
            @ApiResponse(code = 404 , message = "계획 권한을 찾을 수 없습니다. (P0014)" , response = NotFoundAuthorityPlanException.class)

    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 400 , message = "해당 아이디를 찾을 수 없습니다.(U0002)" , response = NotFoundUserException.class),
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다. (P0002)" , response = NotFoundPlanException.class),
            @ApiResponse(code = 404 , message = "계획 권한을 찾을 수 없습니다. (P0014)" , response = NotFoundAuthorityPlanException.class)

    })
    public ResponseEntity<UpdateAuthorityPlanResponse> updateRejectAuthorityPlan(@PathVariable Long authorityPlanId) {
        String loginId = jwtService.getLoginId();
        authorityPlanService.updateRejectAuthorityPlan(authorityPlanId);
        return ResponseEntity.ok(UpdateAuthorityPlanResponse.builder()
                .message("계획 공유 요청을 거절하였습니다.")
                .build());
    }


}
