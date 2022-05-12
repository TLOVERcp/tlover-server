package com.example.tlover.domain.reply.controller;


import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.reply.dto.*;
import com.example.tlover.domain.reply.exception.NotEqualUserIdException;
import com.example.tlover.domain.reply.exception.NotFindReplyException;
import com.example.tlover.domain.reply.service.ReplyService;
import com.example.tlover.global.dto.PaginationDto;
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

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/v1/replys")
@RequiredArgsConstructor
@Api(tags = "Reply API")
public class ReplyApiController {

    private final ReplyService replyService;
    private final JwtService jwtService;

    /**
     * 댓글 조회
     * @param diaryId
     * @return ResponseEntity<List<ReplyGetResponse>>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 조회합니다.", notes = "댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "해당 diaryId로 Diary를 찾을 수 없습니다.(D0004)", response = NotFoundDiaryException.class)
    })
    @GetMapping("/get-reply")
    public ResponseEntity<PaginationDto<List<ReplyGetResponse>>> getReply(@RequestParam Long diaryId,
                                                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(replyService.getReplyList(diaryId, pageable));
    }


    /**
     * 댓글 등록
     * @param replyInsertRequest
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 등록합니다.", notes = "댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "해당 diaryId로 Diary를 찾을 수 없습니다.(D0004)", response = NotFoundDiaryException.class)
    })
    @PostMapping("/insert")
    public ResponseEntity<ReplyResponse> insertReply(@Valid @RequestBody ReplyInsertRequest replyInsertRequest) {
        String loginId = jwtService.getLoginId();
        replyService.insertReply(replyInsertRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 등록했습니다."));
    }

    /**
     * 댓글 수정
     * @param replyUpdateRequest
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 수정합니다.", notes = "댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "댓글 작성자가 일치하지 않습니다.(RP0001) / 댓글 id와 맞는 댓글을 찾을 수 없습니다.(RP0002)", response = NotEqualUserIdException.class),
            @ApiResponse(code = 403, message = "댓글 id와 맞는 댓글을 찾을 수 없습니다.(RP0002)", response = NotFindReplyException.class)
    })
    @PostMapping("/update")
    public ResponseEntity<ReplyResponse> updateReply(@Valid @RequestBody ReplyUpdateRequest replyUpdateRequest) {
        String loginId = jwtService.getLoginId();
        replyService.updateReply(replyUpdateRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 수정했습니다."));
    }

    /**
     * 댓글 삭제
     * @param replyDeleteRequest
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 삭제합니다", notes = "댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "댓글 작성자가 일치하지 않습니다.(RP0001) / 댓글 id와 맞는 댓글을 찾을 수 없습니다.(RP0002)", response = NotEqualUserIdException.class),
            @ApiResponse(code = 403, message = "댓글 id와 맞는 댓글을 찾을 수 없습니다.(RP0002)", response = NotFindReplyException.class)
    })
    @PostMapping("/delete")
    public ResponseEntity<ReplyResponse> deleteReply(@Valid @RequestBody ReplyDeleteRequest replyDeleteRequest) {

        String loginId = jwtService.getLoginId();
        replyService.deleteReply(replyDeleteRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 삭제했습니다."));
    }




}