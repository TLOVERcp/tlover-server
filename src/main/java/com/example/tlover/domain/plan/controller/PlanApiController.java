package com.example.tlover.domain.plan.controller;

import com.example.tlover.domain.authority_plan.service.AuthorityPlanService;
import com.example.tlover.domain.diary.exception.AlreadyExistDiaryException;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.plan.dto.*;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.AlreadyFinishPlanException;
import com.example.tlover.domain.plan.exception.AnotherUserEditingException;
import com.example.tlover.domain.plan.exception.NoAuthorityPlanException;
import com.example.tlover.domain.plan.exception.NotFoundPlanException;
import com.example.tlover.domain.plan.service.PlanService;
import com.example.tlover.domain.plan_region.service.PlanRegionService;
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
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
@Api(tags = "Plan API")
public class PlanApiController {

    @Autowired
    private final PlanService planService;
    @Autowired
    private final PlanRegionService planRegionService;
    @Autowired
    private final AuthorityPlanService authorityPlanService;
    @Autowired
    private final JwtService jwtService;

    /**
     * 계획 작성 API
     * [POST] api/v1/plans/create-plan
     * @param createPlanRequest
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 작성", notes = "계획 작성을 합니다.")
    @PostMapping("/create-plan")
    public ResponseEntity<CreatePlanResponse> CreatePlan(@Valid @RequestBody CreatePlanRequest createPlanRequest){
        String loginId = jwtService.getLoginId();
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
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 목록 조회", notes = "계획 목록을 조회 합니다.")
    @GetMapping("/plan-list")
    public ResponseEntity<List<PlanListResponse>> getPlanList(@RequestParam(required = false) String status) {
        String loginId = jwtService.getLoginId();
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
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 내용 상세 조회", notes = "계획 내용을 상세 조회 합니다.")
    @GetMapping("/plan-detail/{planId}")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다." ,
                    response = NotFoundPlanException.class)
    })
    public ResponseEntity<PlanDetailResponse> getPlanDetail(@PathVariable Long planId) {
        String loginId = jwtService.getLoginId();

        Boolean userAuthority = planService.checkUser(planId, loginId);
        if(!userAuthority)
            throw new NoAuthorityPlanException();

        PlanDetailResponse planDetailResponse = planService.getPlanDetail(planId);
        return ResponseEntity.ok(planDetailResponse);
        }

    /**
     * 계획 삭제 API
     * [POST] api/v1/plans/delete-plan:planId
     * @param planId
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다." ,
                    response = NotFoundPlanException.class)
    })
    @ApiOperation(value = "계획 삭제", notes = "계획을 삭제합니다.")
    @PostMapping("/delete-plan/{planId}")
    public ResponseEntity<DeletePlanResponse> deletePlan(@PathVariable Long planId){
        String loginId = jwtService.getLoginId();

        String planStatus = planService.checkPlanStatus(planId);
        if(planStatus.equals("FINISH"))
            throw new AlreadyFinishPlanException();

        Boolean userAuthority = planService.checkUser(planId, loginId);
        if(!userAuthority)
            throw new NoAuthorityPlanException();

        Plan plan = planService.deletePlan(planId, loginId);
        planRegionService.deletePlanRegion(plan);
        authorityPlanService.deleteAuthorityPlan(plan);
        return ResponseEntity.ok(DeletePlanResponse.builder()
                .message("삭제를 성공하였습니다.")
                .build());
    }

    // 수정중상태로 Editing
    @ApiOperation(value = "계획 상태 수정중으로 수정", notes = "계획 상태를 수정중으로 수정합니다.")
    @PostMapping("/update-plan-editing/{planId}")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다." ,
                    response = NotFoundPlanException.class)
    })
    public ResponseEntity<UpdatePlanStatusResponse> updatePlanStatusEditing(@PathVariable Long planId){
        String loginId = jwtService.getLoginId();

        String planStatus = planService.checkPlanStatus(planId);
        System.out.println(planStatus);
        if(planStatus.equals("EDITING"))
            throw new AnotherUserEditingException();
        if(planStatus.equals("FINISH"))
            throw new AlreadyFinishPlanException(); // 오류 추가하기

        //수정 권한이 있는 유저인지 확인
        Boolean userAuthority = planService.checkUser(planId, loginId);
        if(!userAuthority)
            throw new NoAuthorityPlanException();

        planService.updatePlanStatusEditing(planId);

        return ResponseEntity.ok(UpdatePlanStatusResponse.builder()
                .message("계획 상태 수정을 성공하였습니다.")
                .build());
    }

    /**
     * 계획 수정 API
     * [POST] api/v1/plans/update-plan:planId
     * @param planId
     * @param createPlanRequest
     * @return ResponseEntity
     * @author 류민아
     */
    @ApiOperation(value = "계획 수정", notes = "계획을 수정합니다.")
    @PostMapping("/update-plan/{planId}")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다." ,
                    response = NotFoundPlanException.class)
    })
    public ResponseEntity<UpdatePlanResponse> updatePlan(@PathVariable Long planId,
                                             @Valid @RequestBody CreatePlanRequest createPlanRequest){


       /* String loginId = jwtService.getLoginId();
        Boolean userAuthority = planService.checkUser(planId, loginId);

        if(userAuthority)
            throw new NoAuthorityUserException();*/
        String planStatus = planService.checkPlanStatus(planId);
        System.out.println(planStatus);
        if(!planStatus.equals("EDITING")) throw new RuntimeException("수정불가");
        Plan plan = planService.updatePlan(createPlanRequest, planId);
        planRegionService.updatePlanRegion(createPlanRequest, plan);
        planService.updatePlanStatusActive(planId);
        return ResponseEntity.ok(UpdatePlanResponse.builder()
                .message("계획 수정을 성공하였습니다.")
                .build());
    }

    @ApiOperation(value = "계획 상태 기본으로 수정", notes = "계획 상태를 기본으로 수정합니다.")
    @PostMapping("/update-plan-active/{planId}")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 계획을 찾을 수 없습니다." ,
                    response = NotFoundPlanException.class)
    })
    public ResponseEntity<UpdatePlanStatusResponse> updatePlanStatusActive(@PathVariable Long planId){
        planService.updatePlanStatusActive(planId);
        return ResponseEntity.ok(UpdatePlanStatusResponse.builder()
                .message("계획 상태 수정을 성공하였습니다.")
                .build());
    }


}


