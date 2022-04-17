package com.example.tlover.domain.user_region.controller;

import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionResponse;
import com.example.tlover.domain.user_region.dto.UserRegionResponse;
import com.example.tlover.domain.user_region.service.UserRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/v1/user-regions")
@RequiredArgsConstructor
@Api(tags = "User Region API")
public class UserRegionApiController {

    private final UserApiController userApiController;
    private final UserRegionService userRegionService;

    /**
     * 사용자 관심지역 조회
     * @param httpServletRequest
     * @return ResponseEntity<List<UserRegionResponse>>
     * @author 정혜선
     */
    @ApiOperation(value = "사용자 관심지역 조회", notes = "사용자 관심지역을 조회합니다.")
    @GetMapping("/get-userRegion")
    public ResponseEntity<List<UserRegionResponse>> getUserRegion(HttpServletRequest httpServletRequest) {
        String loginId = userApiController.getLoginIdFromSession(httpServletRequest);
        List<UserRegionResponse> userRegionResponses = userRegionService.getUserRegion(loginId);
        return ResponseEntity.ok(userRegionResponses);
    }

    /**
     * 사용자 관심지역 수정
     * @param updateUserRegionRequest
     * @param httpServletRequest
     * @return ResponseEntity<UpdateUserRegionResponse>
     * @author 정혜선
     */
    @ApiOperation(value = "사용자 관심지역 수정", notes = "사용자 관심지역을 수정합니다.")
    @PostMapping("/update-userRegion")
    public ResponseEntity<UpdateUserRegionResponse> updateUserRegion(@Valid @RequestBody UpdateUserRegionRequest updateUserRegionRequest
                                                                       , HttpServletRequest httpServletRequest) {
        String loginId = userApiController.getLoginIdFromSession(httpServletRequest);
        UpdateUserRegionResponse updateUserRegionResponse = userRegionService.updateUserRegion(updateUserRegionRequest,loginId);
        return ResponseEntity.ok(updateUserRegionResponse);
    }
}
