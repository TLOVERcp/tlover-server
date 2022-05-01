package com.example.tlover.domain.reply.controller;


import com.example.tlover.domain.reply.dto.*;
import com.example.tlover.domain.reply.service.ReplyService;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * @param replyGetRequest
     * @return ResponseEntity<List<ReplyGetResponse>>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 조회합니다.", notes = "댓글을 조회합니다.")
    @PostMapping("/get-reply")
    public ResponseEntity<PaginationDto<List<ReplyGetResponse>>> getReply(@Valid @RequestBody ReplyGetRequest replyGetRequest,
                                                                         @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(replyService.getReplyList(replyGetRequest.getDiaryId(), pageable));
    }


    /**
     * 댓글 등록
     * @param replyInsertRequest, request
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 등록합니다.", notes = "댓글을 등록합니다.")
    @PostMapping("/insert")
    public ResponseEntity<ReplyResponse> insertReply(@Valid @RequestBody ReplyInsertRequest replyInsertRequest,
                                                     HttpServletRequest request) {
        //replyService.checkState(replyInsertRequest.getReplyState());
        String loginId = jwtService.getLoginId();
        replyService.insertReply(replyInsertRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 등록했습니다."));
    }

    /**
     * 댓글 수정
     * @param replyUpdateRequest, request
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 수정합니다.", notes = "댓글을 수정합니다.")
    @PostMapping("/update")
    public ResponseEntity<ReplyResponse> updateReply(@Valid @RequestBody ReplyUpdateRequest replyUpdateRequest,
                                                        HttpServletRequest request) {
        //replyService.checkState(replyUpdateRequest.getReplyState());
        String loginId = jwtService.getLoginId();
        replyService.updateReply(replyUpdateRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 수정했습니다."));
    }

    /**
     * 댓글 삭제
     * @param replyDeleteRequest, request
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 삭제합니다", notes = "댓글을 삭제합니다.")
    @PostMapping("/delete")
    public ResponseEntity<ReplyResponse> deleteReply(@Valid @RequestBody ReplyDeleteRequest replyDeleteRequest) {

        String loginId = jwtService.getLoginId();
        replyService.deleteReply(replyDeleteRequest, loginId);

        return ResponseEntity.ok(ReplyResponse.from("댓글을 삭제했습니다."));
    }




}