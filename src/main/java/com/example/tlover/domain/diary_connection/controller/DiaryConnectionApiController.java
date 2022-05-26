package com.example.tlover.domain.diary_connection.controller;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.exception.NoSuchElementException;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary_connection.constant.DiaryConnectionConstants.EHttpServletRequestAttribute;
import com.example.tlover.domain.diary_connection.constant.DiaryConnectionConstants.EDiaryConnectionResponseMessage;
import com.example.tlover.domain.diary_connection.service.DiaryConnectionService;
import com.example.tlover.domain.history.dto.DeleteHistoryResponse;
import com.example.tlover.domain.history.dto.GetHistoryResponse;
import com.example.tlover.domain.history.exception.NotFoundHistoryException;
import com.example.tlover.domain.history.service.HistoryServiceImpl;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/diaries/connections")
@RequiredArgsConstructor
@Api(tags = "DiaryConnection API")
public class DiaryConnectionApiController {

    private final DiaryConnectionService diaryConnectionService;
    private final HistoryServiceImpl historyService;
    private final JwtServiceImpl jwtService;

    @ApiOperation(value = "다이어리 상세 조회.", notes = "다이어리를 상세히 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "해당 diaryId로 Diary를 찾을 수 없습니다.(D0004)" , response = NotFoundDiaryException.class),
            @ApiResponse(code = 404 , message = "인자를 찾을 수 없습니다.(D0001)" , response = NoSuchElementException.class),
            @ApiResponse(code = 400, message = "해당 아이디를 찾을 수 없습니다.(U0002)", response = NotFoundUserException.class)})
    @GetMapping("/{diaryId}")
    public ResponseEntity<ResponseDto<DiaryInquiryResponse>> getDiaryDetails(@PathVariable Long diaryId, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute(EHttpServletRequestAttribute.eUserId.getAttribute());
        historyService.createHistory(diaryId, userId);
        return ResponseEntity.ok(ResponseDto.create(EDiaryConnectionResponseMessage.eGetDiaryDetailsSuccessMessage.getMessage()
                , this.diaryConnectionService.getDiaryDetails(diaryId, userId)));
    }

    /**
     * 방문 기록 조회
     * [GET] api/v1/history/get-history
     * @return ResponseEntity<List<HistoryResponse>>
     * @author 정혜선
     */
    @ApiOperation(value = "방문 기록 조회", notes = "방문 기록을 조회합니다.")
    @GetMapping("/get-history")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "방문 기록을 찾을 수 없습니다(H0003).", response = NotFoundHistoryException.class)
    })
    public ResponseEntity<ResponseDto<List<GetHistoryResponse>>> getUserRegion() {
        String loginId = jwtService.getLoginId();
        List<GetHistoryResponse> historyResponses = historyService.getUserHistory(loginId);
        return ResponseEntity.ok(ResponseDto.create("방문 기록을 조회했습니다.",historyResponses));
    }

    /**
     * 방문 기록 전체 삭제
     * [GET] api/v1/history/delete-history
     * @return ResponseEntity<DeleteHistoryResponse>
     * @author 정혜선
     */
    @ApiOperation(value = "방문 기록 삭제", notes = "방문 기록을 삭제합니다.")
    @GetMapping("/delete-history")
    public ResponseEntity<DeleteHistoryResponse> delete() {
        String loginId = jwtService.getLoginId();
        historyService.deleteHistory(loginId);
        return ResponseEntity.ok(DeleteHistoryResponse.builder()
                .message("방문 기록이 전체 삭제되었습니다.")
                .build());
    }
}
