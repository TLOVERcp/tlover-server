package com.example.tlover.domain.search.controller;


import com.example.tlover.domain.search.dto.DiarySearchResponse;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.dto.ResponseDto;
import com.example.tlover.global.jwt.service.JwtService;
import com.example.tlover.domain.search.dto.UserSearchResponse;
import com.example.tlover.domain.search.exception.NotFoundSearchDiaryException;
import com.example.tlover.domain.search.exception.NotFoundSearchUserException;
import com.example.tlover.domain.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/search")
@RequiredArgsConstructor
@Api(tags = "Search API")
public class SearchApiController {

    private final SearchService searchService;
    private final JwtService jwtService;

    /**
     * 다이어리 검색 조회
     * @return ResponseEntity<PaginationDto<List<DiarySearchResponse>>>
     * @author 윤여찬
     */
    @ApiOperation(value = "다이어리 검색",notes = "다이어리를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "검색된 다이어리가 없습니다.(S0001)", response = NotFoundSearchDiaryException.class)
    })
    @GetMapping("/get-diary")
    public ResponseEntity<ResponseDto<PaginationDto<List<DiarySearchResponse>>>> searchDiary(@RequestParam String keyword,
                                                                                             @PageableDefault Pageable pageable) {
        Long userId = jwtService.getUserId();
        PaginationDto<List<DiarySearchResponse>> diarySearchResponse = searchService.getSearchedDiary(keyword, userId, pageable);
        return ResponseEntity.ok(ResponseDto.create(diarySearchResponse));
    }

    /**
     * 사용자 검색 조회
     * @return ResponseEntity<PaginationDto<List<UserSearchResponse>>>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 검색",notes = "사용자를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "검색된 사용자가 없습니다.(S0002)", response = NotFoundSearchUserException.class)
    })
    @GetMapping("/get-user")
    public ResponseEntity<ResponseDto<UserSearchResponse>> searchUser(@RequestParam String keyword) {
        UserSearchResponse diarySearchResponse = searchService.getSearchedUser(keyword);
        return ResponseEntity.ok(ResponseDto.create(diarySearchResponse));
    }


}
