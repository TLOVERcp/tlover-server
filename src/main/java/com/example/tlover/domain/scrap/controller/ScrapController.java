package com.example.tlover.domain.scrap.controller;

import com.example.tlover.domain.diary.exception.AlreadyExistDiaryException;
import com.example.tlover.domain.diary.exception.DiaryExceptionList;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.scrap.constant.ScrapConstants.EScrapResponseMessage;
import com.example.tlover.domain.scrap.dto.*;
import com.example.tlover.domain.scrap.service.ScrapService;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/scraps")
@RequiredArgsConstructor
@Api(tags = "Scrap API")
public class ScrapController {

    private final ScrapService scrapService;
    private final static String NOT_FOUND_DIARY_CODE = DiaryExceptionList.NOT_FOUND_DIARY.getCODE();

    /**
     * 다이어리의 스크랩 수 조회
     * @param diaryId
     * @return ResponseDto<ScrapCountResponse>
     * @author 김정우
     */
    @ApiOperation(value = "다이어리의 스크랩 수 조회", notes = "다이어리의 스크랩 수를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "[D0004] 해당 diaryId로 Diary를 찾을 수 없습니다.", response = NotFoundDiaryException.class)})
    @GetMapping("{diaryId}")
    public ResponseEntity<ResponseDto<ScrapCountResponse>> getScrapCount(@PathVariable Long diaryId) {
        return ResponseEntity.ok(ResponseDto.create(EScrapResponseMessage.eScrapCountSuccessMessage.getMessage()
                , this.scrapService.getScrapCount(diaryId)));
    }

    /**
     * 다이어리 스크랩 생성/삭제
     * @param scrapChangeRequest
     * @return ResponseDto<ScrapCountResponse>
     * @author 김정우
     */
    @ApiOperation(value = "다이어리 스크랩 생성/삭제", notes = "다이어리의 스크랩을 생성/삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "[D0004] 해당 diaryId로 Diary를 찾을 수 없습니다.", response = NotFoundDiaryException.class),
            @ApiResponse(code = 400 , message = "[U0002] 해당 아이디를 찾을 수 없습니다.", response = NotFoundUserException.class),
    })
    @PostMapping
    public ResponseEntity<ResponseDto<ScrapChangeResponse>> changeScrap(@Valid @RequestBody ScrapChangeRequest scrapChangeRequest) {
        ScrapChangeResponse scrapChangeResponse = this.scrapService.changeScrap(scrapChangeRequest);
        if(scrapChangeResponse.isCreated()){
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{scrapId}")
                    .buildAndExpand(scrapChangeResponse.getScrapId())
                    .toUri();
            return ResponseEntity.created(location).body(ResponseDto.create(EScrapResponseMessage.eCreateScrapMessage.getMessage(), scrapChangeResponse));
        }
        else return ResponseEntity.noContent().build();
    }

    /**
     * 해당 유저의 해당 다이어리 스크랩 여부 조회
     * @param scrapOrNotRequest
     * @return ResponseDto<ScrapOrNotResponse>
     * @author 김정우
     */
    @ApiOperation(value = "해당 유저의 해당 다이어리 스크랩 여부 조회", notes = "해당 유저의 해당 다이어리 스크랩 여부를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404 , message = "[D0004] 해당 diaryId로 Diary를 찾을 수 없습니다.", response = NotFoundDiaryException.class),
            @ApiResponse(code = 400 , message = "[U0002] 해당 아이디를 찾을 수 없습니다.", response = NotFoundUserException.class),
    })
    @GetMapping("/whether")
    public ResponseEntity<ResponseDto<ScrapOrNotResponse>> getScrapOrNot(@Valid @RequestBody ScrapOrNotRequest scrapOrNotRequest) {
        return ResponseEntity.ok(ResponseDto.create(EScrapResponseMessage.eScrapOrNotMessage.getMessage()
                , this.scrapService.getScrapOrNot(scrapOrNotRequest)));
    }

    /**
     * 모든 다이어리 스크랩 많은 순 조회
     * @param pageable
     * @return ResponseDto<PaginationDto<List<DiaryInquiryByScrapRankingResponse>>>
     * @author 김정우
     */

    @ApiOperation(value = "모든 다이어리 스크랩 많은 순 조회", notes = "모든 다이어리를 스크랩 많은 순으로 조회합니다.")
    @GetMapping("/ranking")
    public ResponseEntity<ResponseDto<PaginationDto<List<DiaryInquiryByScrapRankingResponse>>>> getDiariesByScrapRanking(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(ResponseDto.create(EScrapResponseMessage.eGetDiariesByScrapRankingMessage.getMessage()
                , this.scrapService.getDiariesByScrapRanking(pageable)));
    }



}
