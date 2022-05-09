package com.example.tlover.domain.history.controller;

import com.example.tlover.domain.history.dto.CreateHistoryRequest;
import com.example.tlover.domain.history.dto.CreateHistoryResponse;
import com.example.tlover.domain.history.dto.DeleteHistoryResponse;
import com.example.tlover.domain.history.dto.GetHistoryResponse;
import com.example.tlover.domain.history.exception.NotFoundHistoryException;
import com.example.tlover.domain.history.exception.RejectDeletedDiaryException;
import com.example.tlover.domain.history.exception.RejectGetDiaryException;
import com.example.tlover.domain.history.service.HistoryServiceImpl;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/history")
@RequiredArgsConstructor
@Api(tags = "History API")
public class HistoryApiController {

    private final JwtServiceImpl jwtService;
    private final HistoryServiceImpl historyService;

    /**
     * 방문 기록 생성
     * [POST] api/v1/history/create-history
     * @param createHistoryRequest
     * @return ResponseEntity<CreateHistoryResponse>
     * @author 정혜선
     */
    @ApiOperation(value = "방문 기록 생성", notes = "방문 기록을 생성합니다.")
    @PostMapping(value = "/create-history")
    @ApiResponses(value = {
            @ApiResponse(code = 401 , message = "해당 다이어리는 조회할 수 없는 상태입니다.(H0001)" , response = RejectGetDiaryException.class),
            @ApiResponse(code = 400 , message = "해당 다이어리는 삭제된 상태입니다.(H0002)" , response = RejectDeletedDiaryException.class)
    })
    public ResponseEntity<CreateHistoryResponse> createHistory(@Valid @RequestBody CreateHistoryRequest createHistoryRequest) {
        String loginId = jwtService.getLoginId();
        historyService.createHistory(loginId, createHistoryRequest);
        return ResponseEntity.ok(CreateHistoryResponse.builder()
                .message("방문 기록을 생성했습니다.")
                .build());
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
