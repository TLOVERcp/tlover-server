package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.AlreadyExistDiaryException;
import com.example.tlover.domain.diary.exception.NotAuthorityDeleteException;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.exception.dto.ApiErrorResponse;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
     * 뷰가 정확하게 나오지 않아서 그냥 다 조회해버렸습니다 ^~^
     * @return ResponseEntity<List<DiaryInquiryResponse>>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 조회",notes = "다이어를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "JWT 토큰이 비어있습니다.", response = ApiErrorResponse.class),
            @ApiResponse(code = 302, message = "REFRESH-TOKEN이 만료되었습니다. \n ACCESS-TOKEN이 만료되었습니다.", response = ApiErrorResponse.class),
    })
    @GetMapping("/get-diary")
    public ResponseEntity<List<DiaryInquiryResponse>> getDiary(){
//        String loginId = userApiController.getLoginIdFromSession(request);
        List<DiaryInquiryResponse> diaryInquiryResponse = diaryService.getDiary();
        return ResponseEntity.ok(diaryInquiryResponse);
    }

    /**
     * 갈만한 여행지 조회
     * @return ResponseEntity<List<DiaryInquiryResponse>>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 갈만한 여행지 조회", notes = "홈 화면의 두번째 조회 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "JWT 토큰이 비어있습니다.", response = ApiErrorResponse.class),
            @ApiResponse(code = 302, message = "REFRESH-TOKEN이 만료되었습니다. \n ACCESS-TOKEN이 만료되었습니다.", response = ApiErrorResponse.class),
    })
    @GetMapping("/get-goingdiary")
    public ResponseEntity<List<DiaryInquiryResponse>> getGoingDiary(){
        List<DiaryInquiryResponse> diaryInquiryResponses = diaryService.getGoingDiary();
        return ResponseEntity.ok(diaryInquiryResponses);
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
            @ApiResponse(code = 500 , message = "하나의 유저는 하나의 계획에 한번만 작성 가능합니다." ,
            response = AlreadyExistDiaryException.class
            ) ,
            @ApiResponse(code = 404 , message = "해당 diaryId로 Diary를 찾을 수 없습니다." ,
            response = NotFoundDiaryException.class)
    })
    @PostMapping(value = "/create-diary")
    public ResponseEntity<ResponseDto<CreateDiaryResponse>> CreateDiary(@Valid CreateDiaryRequest createDiaryRequest , HttpServletRequest request) {
        String loginId = jwtService.getLoginId();

        return ResponseEntity.ok(ResponseDto.create("다이어리 작성이 완료되었습니다." ,    diaryService.createDiary(createDiaryRequest, loginId)));
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
            @ApiResponse(code = 403 , message = "다이어리의 삭제 권한이 없습니다." ,
                    response = NotAuthorityDeleteException.class
            ) ,
            @ApiResponse(code = 404 , message = "해당 diaryId로 Diary를 찾을 수 없습니다." ,
                    response = NotFoundDiaryException.class)
            })
    @PostMapping(value = "/delete-diary/{diaryId}")
    public ResponseEntity<ResponseDto<DeleteDiaryResponse>> DeleteDiary(@PathVariable Long diaryId) {
        String loginId = jwtService.getLoginId();
        return ResponseEntity.ok(ResponseDto.create("삭제가 완료 되었습니다" , diaryService.deleteDiary(diaryId, loginId)));
    }

    /**
     * 다이어리 수정 API
     * @param modifyDiaryRequest
     * @param request
     * @return ResponseEntity<String>
     * @author 한규범
     */
    @ApiOperation(value = "다이어리 수정", notes = "다이어리를 수정합니다.")
    @PostMapping(value = "/modify-diary/{diaryId}")
    public ResponseEntity<String> ModifyDiary(@Valid ModifyDiaryRequest modifyDiaryRequest, HttpServletRequest request){
        String loginId = jwtService.getLoginId();
        Diary diary = diaryService.modifyDiary(modifyDiaryRequest, loginId);
        return ResponseEntity.ok("다이어리 수정이 완료되었습니다.");
    }

    /**
     * 다이어리에 좋아요를 누르거나 좋아요 취소하기
     * @param diaryId
     * author 신동민
     */

    @ApiOperation(value = "다이어리 좋아요 누르기" , notes = "다이어리 좋아요를 누르거나 취소합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 403 , message = "다이어리의 삭제 권한이 없습니다." ,
                    response = NotAuthorityDeleteException.class
            ) ,
            @ApiResponse(code = 404 , message = "해당 diaryId로 Diary를 찾을 수 없습니다." ,
                    response = NotFoundDiaryException.class)
    })
    @PostMapping(value = "/liked/{diaryId}")
        public ResponseEntity<ResponseDto<DiaryLikedChangeResponse>> DiaryLikedChange(@PathVariable Long diaryId) {
            String loginId = jwtService.getLoginId();
            DiaryLikedChangeResponse diaryLikedChangeResponse = diaryService.diaryLikedChange(diaryId, loginId);

            if(diaryLikedChangeResponse.isLiked()) {
                return ResponseEntity.ok(ResponseDto.create("좋아요"  , diaryLikedChangeResponse));
            }

            else {
                return ResponseEntity.ok(ResponseDto.create("좋아요 취소"  , diaryLikedChangeResponse));
            }
    }

    /**
     * 좋아요의 갯수 가져오기
     * @param diaryId
     * @return
     * author 신동민
     */

    @ApiOperation(value = "좋아요 갯수 조회", notes = "특정 다이어리의 좋아요 갯수를 조회합니다.")
    @PostMapping(value = "/liked-views/{diaryId}")
    public ResponseEntity<ResponseDto<DiaryLikedViewsResponse>> DiaryLikeViews(@PathVariable Long diaryId) {
        return ResponseEntity.ok(ResponseDto.create("테스트" , diaryService.getDiaryViews(diaryId)));
    }


    /**
     * 다이어리 조회
     * @return
     */
    @ApiOperation(value = "홈 화면 여행 취향 다이어리 조회", notes = "나와 여행 취향이 닮은 사람들에 대한 다이어리를 조회합니다.")
    @GetMapping(value = "/get-diary-prefer")
    public ResponseEntity<List<DiaryPreferenceResponse>> getDiaryPreference(){
        String loginId = jwtService.getLoginId();
        List<DiaryPreferenceResponse> diaryInquiryResponse = diaryService.getDiaryPreference(loginId);
        return ResponseEntity.ok(diaryInquiryResponse);
    }
}
