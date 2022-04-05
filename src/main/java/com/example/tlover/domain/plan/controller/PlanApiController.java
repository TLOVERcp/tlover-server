package com.example.tlover.domain.plan.controller;

import com.example.tlover.domain.plan.dto.CreatePlanRequest;
import com.example.tlover.domain.plan.dto.CreatePlanResponse;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.service.PlanService;
import com.example.tlover.domain.plan_region.dto.CreatePlanRegionRequest;
import com.example.tlover.domain.plan_region.entity.PlanRegion;
import com.example.tlover.domain.plan_region.service.PlanRegionService;
import com.example.tlover.domain.user.controller.UserApiController;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
@Api(tags = "Plan API")
public class PlanApiController {

    private final PlanService planService;
    private final PlanRegionService planRegionService;
    private final UserApiController userApiController;

    @PostMapping("/createPlan")
    public ResponseEntity<CreatePlanResponse> CreatePlan(@Valid @RequestBody CreatePlanRequest createPlanRequest,
                                                         HttpServletRequest request){
        String loginId = userApiController.getLoginIdFromSession(request);
        Plan plan = planService.createPlan(createPlanRequest, loginId);
        planRegionService.createPlanRegion(createPlanRequest, plan);
        return ResponseEntity.ok(CreatePlanResponse.builder()
                .message("계획 작성 완료")
                .build());

    }



}
