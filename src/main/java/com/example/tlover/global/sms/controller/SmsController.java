package com.example.tlover.global.sms.controller;

import com.example.tlover.domain.user.dto.CertifiedValue;
import com.example.tlover.global.sms.dto.SmsSendRequest;
import com.example.tlover.global.sms.dto.SmsSendResponse;
import com.example.tlover.global.sms.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("api/v1/sms")
@RequiredArgsConstructor
@Api(tags = "SMS API")
public class SmsController {

    private final SmsService smsService;

    @ApiOperation(value = "문자발송", notes = "문자를 발송합니다.")
    @PostMapping("/send")
    public ResponseEntity<SmsSendResponse> smsSend(@Valid @RequestBody SmsSendRequest smsSendRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        String value = smsService.sendSms(smsSendRequest);

        return ResponseEntity.ok(SmsSendResponse.builder()
                .value(value)
                .build());
    }

    /**
     * 아이디 찾기 인증문자 발송
     * @param smsSendRequest, request
     * @return ResponseEntity<SmsSendResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "아이디 찾기 인증문자 발송", notes = "아이디 찾기 인증문자를 발송합니다.")
    @PostMapping("/find-id")
    public ResponseEntity<SmsSendResponse> smsSendFindId(@Valid @RequestBody SmsSendRequest smsSendRequest,
                                                         HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        String value = smsService.sendSms(smsSendRequest);
        CertifiedValue certifiedValue = new CertifiedValue();
        certifiedValue.setFindLoginId(value);
        request.getSession().setAttribute("certifiedValue", certifiedValue);

        return ResponseEntity.ok(SmsSendResponse.builder()
                .message("인증코드를 발송했습니다.")
                .build());
    }

    /**
     * 비밀번호 찾기 인증문자 발송
     * @param smsSendRequest, request
     * @return ResponseEntity<SmsSendResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "비밀번호 찾기 인증문자 발송", notes = "비밀번호 찾기 인증문자를 발송합니다.")
    @PostMapping("/find-password")
    public ResponseEntity<SmsSendResponse> smsSendFindPassword(@Valid @RequestBody SmsSendRequest smsSendRequest,
                                                               HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        String value = smsService.sendSms(smsSendRequest);
        CertifiedValue certifiedValue = new CertifiedValue();
        certifiedValue.setFindPassword(value);
        request.getSession().setAttribute("certifiedValue", certifiedValue);

        return ResponseEntity.ok(SmsSendResponse.builder()
                .message("인증코드를 발송했습니다.")
                .build());
    }

}
