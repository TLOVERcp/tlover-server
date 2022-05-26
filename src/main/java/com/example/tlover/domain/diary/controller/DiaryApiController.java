package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.*;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.exception.dto.ApiErrorResponse;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
@Api(tags = "Diary API")
public class DiaryApiController {

    private final UserApiController userApiController;
    private final DiaryService diaryService;
    private final JwtService jwtService;


    /**
     * 다이어리 수정중으로 상태 변환 api
     * @param diaryId
     * author : 신동민
     */
    @ApiOperation(value = "다이어리 상태를 수정중으로 변환", notes = "다이어리를 수정하거나 등록을 시작하는 시점에 상태를 수정중으로 변환합니다.")
    @PostMapping("/update-diary-editing/{diaryId}")
    public ResponseEntity<ResponseDto<UpdateDiaryStatusResponse>> updateDiaryStatusEditing(@PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("다이어리의 변환된 상태를 반환" , diaryService.updateDiaryEditing(loginId, diaryId)));
    }


    /**
     * 다이어리 작성 api
     * swagger url => [post]  api/v1/plans/create-diary
     * @param createDiaryRequest
     * @param request
     * @return ResponseEntity<CreateDiaryResponse>
     * author => 신동민
     */
    @ApiOperation(value = "다이어리 작성", notes = "다이어리 작성을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "하나의 유저는 하나의 계획에 한번만 작성 가능합니다.",
                    response = AlreadyExistDiaryException.class
            ),
            @ApiResponse(code = 404, message = "해당 diaryId로 Diary를 찾을 수 없습니다.",
                    response = NotFoundDiaryException.class)
    })
    @PostMapping(value = "/create-diary")
    public ResponseEntity<ResponseDto<CreateDiaryResponse>> CreateDiary(@Valid CreateDiaryRequest createDiaryRequest, HttpServletRequest request) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("다이어리 작성이 완료되었습니다.", diaryService.createDiary(createDiaryRequest, loginId)));
    }



    /**
     * 다이어리 작성폼 api
     * @param planId
     * author : 신동민
     */
    @ApiOperation(value = "다이어리 작성폼", notes = "다이어리 작성폼을을 불러옵니다.")
    @GetMapping(value = "/create-diary/{planId}")
    public ResponseEntity<ResponseDto<CreateDiaryFormResponse>> getCreateDiaryForm(@PathVariable Long planId) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("다이어리 작성 양식을 불러왔습니다." , diaryService.getCreateDiaryForm(planId,loginId)));
    }


    /**
     * 다이어리 작성완료로 상태변경
     * @param diaryId
     * @param planId
     * @return
     */
    @PostMapping(value = "/finish-diary/{planId}/{diaryId}")
    public ResponseEntity<ResponseDto<CreateDiaryResponse>> finishDiary(@PathVariable Long planId, @PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        diaryService.completeDiary(loginId , planId , diaryId);
        return null;
    }


    /** DiaryLikedRanking
     * 좋아요순으로 다이어리 보여주기
     */
    @ApiOperation(value = "좋아요순으로 다이어리 보여주기" , notes = "특정 다이어리의 좋아요 갯수를 조회합니다.")
    @PostMapping(value = "/liked-ranking")
    public ResponseEntity<ResponseDto<PaginationDto<List<DiaryInquiryByLikedRankingResponse>>>> DiaryLikedRanking(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(ResponseDto.create("모든 다이어리를 좋아요 순으로 조회합니다."
                , this.diaryService.getDiaryByLikedRanking(pageable)));
    }

    /**
     * 다이어리 삭제 API
     * swagger url => [post]  api/v1/plans/delete-diary/{diaryId}
     * @param diaryId
     * @return ResponseEntity<CreateDiaryResponse>
     * author => 신동민
     */

    @ApiOperation(value = "다이어리 삭제", notes = "다이어리를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "다이어리의 삭제 권한이 없습니다.",
                    response = NotAuthorityDeleteException.class
            ),
            @ApiResponse(code = 404, message = "해당 diaryId로 Diary를 찾을 수 없습니다.",
                    response = NotFoundDiaryException.class)
    })
    @PostMapping(value = "/delete-diary/{diaryId}")
    public ResponseEntity<ResponseDto<DeleteDiaryResponse>> DeleteDiary(@PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("삭제가 완료 되었습니다", diaryService.deleteDiary(diaryId, loginId)));
    }

    /**
     * 다이어리 수정폼 api
     * @param diaryId
     * @return
     * author : 신동민
     */
    @ApiOperation(value = "다이어리 수정폼", notes = "다이어리 수정폼을 불러옵니다.")
    @PostMapping(value = "/modify-diary/{diaryId}")
    public ResponseEntity<ResponseDto<ModifyDiaryFormResponse>> getModifyDiaryForm(@PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("다이어리 수정이 완료되었습니다." ,  diaryService.getModifyDiaryForm(diaryId, loginId)));
    }

    /**
     * 다이어리 수정 API
     * @param modifyDiaryRequest
     * @return ResponseEntity<String>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 수정", notes = "다이어리를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "JWT 토큰이 비어있습니다. \n 해당 diaryId로 Diary를 찾을 수 없습니다.", response = ApiErrorResponse.class),
            @ApiResponse(code = 302, message = "REFRESH-TOKEN이 만료되었습니다. \n ACCESS-TOKEN이 만료되었습니다.", response = ApiErrorResponse.class),
            @ApiResponse(code = 403, message = "다이어리의 수정 권한이 없습니다.", response = ApiErrorResponse.class)

    })
    @PostMapping(value = "/modify-diary")
    public ResponseEntity<ResponseDto<String>> ModifyDiary(ModifyDiaryRequest modifyDiaryRequest) {
        String loginId = jwtService.getLoginId();
        diaryService.modifyDiary(modifyDiaryRequest, loginId);
        return ResponseEntity.ok(ResponseDto.create("다이어리 수정이 완료되었습니다."));
    }

    /**
     * 다이어리에 좋아요를 누르거나 좋아요 취소하기
     *
     * @param diaryId author 신동민
     */

    @ApiOperation(value = "다이어리 좋아요 누르기", notes = "다이어리 좋아요를 누르거나 취소합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "다이어리의 삭제 권한이 없습니다.",
                    response = NotAuthorityDeleteException.class
            ),
            @ApiResponse(code = 404, message = "해당 diaryId로 Diary를 찾을 수 없습니다.",
                    response = NotFoundDiaryException.class)
    })
    @PostMapping(value = "/liked/{diaryId}")
    public ResponseEntity<ResponseDto<DiaryLikedChangeResponse>> DiaryLikedChange(@PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        DiaryLikedChangeResponse diaryLikedChangeResponse = diaryService.diaryLikedChange(diaryId, loginId);

        if (diaryLikedChangeResponse.isLiked()) {
            return ResponseEntity.ok(ResponseDto.create("좋아요", diaryLikedChangeResponse));
        } else {
            return ResponseEntity.ok(ResponseDto.create("좋아요 취소", diaryLikedChangeResponse));
        }
    }

    /**
     * 좋아요의 갯수 가져오기
     * @param diaryId
     * @return author 신동민
     */

    @ApiOperation(value = "좋아요 갯수 조회", notes = "특정 다이어리의 좋아요 갯수를 조회합니다.")
    @PostMapping(value = "/liked-views/{diaryId}")
    public ResponseEntity<ResponseDto<DiaryLikedViewsResponse>> DiaryLikeViews(@PathVariable Long diaryId) {
        return ResponseEntity.ok(ResponseDto.create("테스트", diaryService.getDiaryViews(diaryId)));
    }

    /**
     * 홈 화면 여행 취향 다이어리 조회
     * @return
     * @Author 한규범
     */
    @ApiOperation(value = "홈 화면 여행 취향 다이어리 조회", notes = "나와 여행 취향이 닮은 사람들에 대한 다이어리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "JWT 토큰이 비어있습니다.", response = ApiErrorResponse.class),
            @ApiResponse(code = 302, message = "REFRESH-TOKEN이 만료되었습니다. \n ACCESS-TOKEN이 만료되었습니다.", response = ApiErrorResponse.class),
    })
    @GetMapping(value = "/get-diary-prefer")
    public ResponseEntity<ResponseDto<List<DiaryPreferenceResponse>>> getDiaryPreference() {
        String loginId = jwtService.getLoginId();
        List<DiaryPreferenceResponse> diaryInquiryResponse = diaryService.getDiaryPreference(loginId);
        return ResponseEntity.ok(ResponseDto.create(diaryInquiryResponse));
    }

    /**
     * 내가 작성한 다이어리 목록 조회
     * [GET] api/v1/diaries/my-diaries
     * @return ResponseEntity<List<MyDiaryListResponse>>
     * @author 정혜선
     */
    @ApiOperation(value = "내가 작성한 다이어리 목록 조회", notes = "내가 작성한 다이어리 목록을 조회합니다.")
    @GetMapping(value = "/my-diaries")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "내가 작성한 다이어리를 찾을 수 없습니다(D0006).", response = NotFoundMyDiaryException.class),
    })
    public ResponseEntity<ResponseDto<List<MyDiaryListResponse>>> getMyDiaries() {
        String loginId = jwtService.getLoginId();
        List<MyDiaryListResponse> myDiaryListResponses = diaryService.getDiaryList(loginId);
        return ResponseEntity.ok(ResponseDto.create(myDiaryListResponses));
    }

    /**
     * 내가 초대된 다이어리 목록 조회
     * [GET] api/v1/diaries/invited-diaries
     * @return ResponseEntity<ResponseDto<List<MyDiaryListResponse>>>
     * @author 정혜선
     */
    @ApiOperation(value = "내가 초대된 다이어리 목록 조회", notes = "내가 초대된 다이어리 목록을 조회합니다.")
    @GetMapping(value = "/invited-diaries")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "내가 초대된 다이어리를 찾을 수 없습니다(D0007).", response = NotFoundAcceptDiaryException.class),
    })
    public ResponseEntity<ResponseDto<List<MyDiaryListResponse>>> getAcceptDiaries() {
        String loginId = jwtService.getLoginId();
        List<MyDiaryListResponse> myDiaryListResponses = diaryService.getAcceptDiaryList(loginId);
        return ResponseEntity.ok(ResponseDto.create(myDiaryListResponses));
    }

    /**
     * 홈 화면 여행 취향 다이어리 조회
     * @return  ResponseEntity<ResponseDto<List<DiaryWeatherResponse>>>
     * @Author 한규범
     */
    @ApiOperation(value = "홈 화면 다이어리 관광 기후 지수 기반 추천 조회", notes = "금일 날씨를 기반으로 ")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "JWT 토큰이 비어있습니다. \n 국내에 관광기후 매우 좋음이 없습니다. ", response = ApiErrorResponse.class),
            @ApiResponse(code = 302, message = "REFRESH-TOKEN이 만료되었습니다. \n ACCESS-TOKEN이 만료되었습니다.", response = ApiErrorResponse.class),
    })
    @GetMapping(value = "/get-diary-weather")
    public ResponseEntity<ResponseDto<List<DiaryWeatherResponse>>> getWeatherDiaries() {
        String loginId = jwtService.getLoginId();
        List<DiaryWeatherResponse> diaryWeatherResponses = diaryService.getDiaryWeather(loginId);
        return ResponseEntity.ok(ResponseDto.create(diaryWeatherResponses));
    }

}
