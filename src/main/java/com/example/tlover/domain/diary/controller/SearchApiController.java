package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.AlreadyExistDiaryException;
import com.example.tlover.domain.diary.exception.NotAuthorityDeleteException;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.exception.NotFoundSearchDiaryException;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.exception.dto.ApiErrorResponse;
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
@RequestMapping("api/v1/search")
@RequiredArgsConstructor
@Api(tags = "Search API")
public class SearchApiController {

    private final DiaryService diaryService;

    /**
     * 다이어리 검색 조회
     * @return ResponseEntity<PaginationDto<List<DiarySearchResponse>>>
     * @author 윤여찬
     */
    @ApiOperation(value = "다이어리 검색",notes = "다이어리를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "검색된 다이어리가 없습니다.", response = NotFoundSearchDiaryException.class)
    })
    @GetMapping("/get-diary")
    public ResponseEntity<PaginationDto<List<DiarySearchResponse>>> searchDiary(@RequestParam String keyword,
                                                                               @PageableDefault Pageable pageable) {
        PaginationDto<List<DiarySearchResponse>> diarySearchResponse = diaryService.getSearchedDiary(keyword, pageable);
        return ResponseEntity.ok(diarySearchResponse);
    }


}
