package com.example.tlover.domain.reply.controller;


import com.example.tlover.domain.reply.dto.ReplyRequest;
import com.example.tlover.domain.reply.dto.ReplyResponse;
import com.example.tlover.domain.reply.dto.ReplyUpdateRequest;
import com.example.tlover.domain.reply.service.ReplyService;
import com.example.tlover.domain.user.controller.UserApiController;
import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.DeniedAccessExceptioin;
import com.example.tlover.domain.user.exception.NotCertifiedValueException;
import com.example.tlover.domain.user.service.OAuth2UserServiceGoogle;
import com.example.tlover.domain.user.service.OAuth2UserServiceKakao;
import com.example.tlover.domain.user.service.OAuth2UserServiceNaver;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.domain.user_refreshtoken.service.UserRefreshTokenService;
import com.example.tlover.domain.user_thema.service.UserThemaService;
import com.example.tlover.global.jwt.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("api/v1/replys")
@RequiredArgsConstructor
@Api(tags = "Reply API")
public class ReplyApiController {

    private final UserApiController userApiController;
    private final UserService userService;
    private final ReplyService replyService;


    /**
     * 댓글 등록
     * @param replyRequest, request
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬, 토큰관련 : 한규범
     */
    @ApiOperation(value = "댓글을 등록합니다.", notes = "댓글을 등록합니다.")
    @PostMapping("/insert")
    public ResponseEntity<ReplyResponse> insertReply(@Valid @RequestBody ReplyRequest replyRequest,
                                                     HttpServletRequest request) {

        String loginId = userApiController.getLoginIdFromSession(request);
        replyService.insertReply(replyRequest, loginId);


        return null;
    }

    /**
     * 댓글 수정
     * @param replyRequest, request
     * @return ResponseEntity<ReplyResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 수정합니다.", notes = "댓글을 수정합니다.")
    @PostMapping("/update")
    public ResponseEntity<ReplyResponse> updateReply(@Valid @RequestBody ReplyUpdateRequest replyUpdateRequest,
                                                     HttpServletRequest request) {

        String loginId = userApiController.getLoginIdFromSession(request);
        replyService.updateReply(replyUpdateRequest, loginId);

        return null;
    }

    /**
     * 댓글 삭제
     * @param replyRequest, request
     * @return ResponseEntity<DuplicateResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "댓글을 삭제합니다", notes = "댓글을 삭제합니다.")
    @PostMapping("/delete")
    public ResponseEntity<ReplyResponse> deleteReply(@Valid @RequestBody ReplyUpdateRequest replyUpdateRequest,
                                                         HttpServletRequest request) {

        String loginId = userApiController.getLoginIdFromSession(request);
        replyService.deleteReply(replyUpdateRequest, loginId);

        return null;
    }




}