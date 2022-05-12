package com.example.tlover.domain.rating.controller;

import com.example.tlover.domain.plan.exception.NotFoundPlanException;
import com.example.tlover.domain.rating.dto.RatingGetResponse;
import com.example.tlover.domain.rating.exception.NotFoundRatingException;
import com.example.tlover.domain.rating.service.RatingService;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/ratings")
@RequiredArgsConstructor
@Api(tags = "Rating API")
public class RatingApiController {

    private final JwtService jwtService;
    private final RatingService ratingService;


    @ApiOperation(value = "등급 조회", notes = "유저별 등급을 조회 합니다.")
    @GetMapping("/get-rating")
    @ApiResponses(value = {
            @ApiResponse(code = 400 , message = "해당 아이디를 찾을 수 없습니다.(U0002)" , response = NotFoundUserException.class),
            @ApiResponse(code = 404 , message = "해당 등급을 찾을 수 없습니다.(r0002)" , response = NotFoundRatingException.class)

    })
    public ResponseEntity<ResponseDto<RatingGetResponse>> getRating(){
        String loginId = jwtService.getLoginId();
        ratingService.updateRating(loginId);
        RatingGetResponse ratingGetResponse = ratingService.getRating(loginId);
        return ResponseEntity.ok(ResponseDto.create("조회 성공",ratingGetResponse));
    }


}
