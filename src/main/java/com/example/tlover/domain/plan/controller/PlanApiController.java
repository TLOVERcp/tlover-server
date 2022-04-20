package com.example.tlover.domain.plan.controller;

import com.example.tlover.domain.authority_plan.service.AuthorityPlanService;
import com.example.tlover.domain.plan.dto.*;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.NoAuthorityUserException;
import com.example.tlover.domain.plan.service.PlanService;
import com.example.tlover.domain.plan_region.service.PlanRegionService;
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
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
@Api(tags = "Plan API")
public class PlanApiController {

    private final PlanService planService;
    private final PlanRegionService planRegionService;
    private final AuthorityPlanService authorityPlanService;
    private final UserApiController userApiController;

    /**
     * 계획 작성 API
     * [POST] api/v1/plans/create-plan
     * @param createPlanRequest
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 작성", notes = "계획 작성을 합니다.")
    @PostMapping("/create-plan")
    public ResponseEntity<CreatePlanResponse> CreatePlan(@Valid @RequestBody CreatePlanRequest createPlanRequest,
                                                         HttpServletRequest request){
        String loginId = userApiController.getLoginIdFromSession(request);
        Plan plan = planService.createPlan(createPlanRequest, loginId);
        planRegionService.createPlanRegion(createPlanRequest, plan);
        authorityPlanService.addPlanUser(plan, loginId);
        return ResponseEntity.ok(CreatePlanResponse.builder()
                .message("계획 작성을 성공하였습니다.")
                .build());
    }

    /**
     * 유저별 계획 목록 전체 조회 API
     * [GET] api/v1/plans/plan-list
     * 유저/상태별 계획 목록 조회 API
     * [GET] api/v1/plans/plan-list?status=
     * @param status
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 목록 조회", notes = "계획 목록을 조회 합니다.")
    @GetMapping("/plan-list")
    public ResponseEntity<List<PlanListResponse>> getPlanList(@RequestParam(required = false) String status, HttpServletRequest request) {
        String loginId = userApiController.getLoginIdFromSession(request);
            if (status == null) {
                List<PlanListResponse> planListResponses = planService.getAllPlans(loginId);
                return ResponseEntity.ok(planListResponses);
            } else {
                List<PlanListResponse> planListResponses = planService.getPlansByState(loginId, status);
                return ResponseEntity.ok(planListResponses);
            }
    }

    /**
     * 계획내용 상세 조회 API
     * [GET] api/v1/plans/plan-detail/:planId
     * @param planId
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 내용 상세 조회", notes = "계획 내용을 상세 조회 합니다.")
    @GetMapping("/plan-detail/{planId}")
    public ResponseEntity<PlanDetailResponse> getPlanDetail(@PathVariable Long planId, HttpServletRequest request) {
        String loginId = userApiController.getLoginIdFromSession(request);
        Boolean userAuthority = planService.checkUser(planId, loginId);

        if(userAuthority)
            throw new NoAuthorityUserException();

        PlanDetailResponse planDetailResponse = planService.getPlanDetail(planId);
        // 유저 확인
        return ResponseEntity.ok(planDetailResponse);
        }

    /**
     * 계획 삭제 API
     * [POST] api/v1/plans/delete-plan:planId
     * @param planId
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 삭제", notes = "계획을 삭제합니다.")
    @PostMapping("/delete-plan/{planId}")
    public ResponseEntity<DeletePlanResponse> deletePlan(@PathVariable Long planId, HttpServletRequest request){
        String loginId = userApiController.getLoginIdFromSession(request);
        Boolean userAuthority = planService.checkUser(planId, loginId);

        if(userAuthority)
           throw new NoAuthorityUserException();

        Plan plan = planService.deletePlan(planId);
        planRegionService.deletePlanRegion(plan);
        authorityPlanService.deleteAuthorityPlan(plan);
        return ResponseEntity.ok(DeletePlanResponse.builder()
                .message("삭제를 성공하였습니다.")
                .build());
    }

    /**
     * 계획 수정 API
     * [POST] api/v1/plans/update-plan:planId
     * @param planId
     * @param createPlanRequest
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 수정", notes = "계획을 수정합니다.")
    @PostMapping("/update-plan/{planId}")
    public ResponseEntity<UpdatePlanResponse> updatePlan(@PathVariable Long planId,
                                             @Valid @RequestBody CreatePlanRequest createPlanRequest,
                                             HttpServletRequest request){
        String loginId = userApiController.getLoginIdFromSession(request);
        Boolean userAuthority = planService.checkUser(planId, loginId);

        if(userAuthority)
            throw new NoAuthorityUserException();

        Plan plan = planService.updatePlan(createPlanRequest, planId);
        planRegionService.updatePlanRegion(createPlanRequest, plan);
        return ResponseEntity.ok(UpdatePlanResponse.builder()
                .message("계획 수정을 성공하였습니다.")
                .build());
    }

}


