package com.example.tlover.domain.plan.controller;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.plan.dto.PlanDetailResponse;
import com.example.tlover.domain.plan.dto.PlanListResponse;
import com.example.tlover.domain.plan.entity.Plan;
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
    private final UserApiController userApiController;

    /**
     * 계획 작성 API
     * [POST] api/v1/plans/createPlan
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
        return ResponseEntity.ok(CreatePlanResponse.builder()
                .message("계획 작성 완료")
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
     * [GET] api/v1/plans/plan-list?status=
     * @param planTitle
     * @param request
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 내용 상세 조회", notes = "계획 내용을 상세 조회 합니다.")
    @GetMapping("/plan-detail/{planTitle}")
    public ResponseEntity<PlanDetailResponse> getPlanDetail(@PathVariable String planTitle, HttpServletRequest request) {
        String loginId = userApiController.getLoginIdFromSession(request);
        PlanDetailResponse planDetailResponse = planService.getPlanDetail(planTitle, loginId);
        return ResponseEntity.ok(planDetailResponse);
        }
    }


