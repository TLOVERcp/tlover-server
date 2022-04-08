package com.example.tlover.global.sms.controller;

import com.example.tlover.global.sms.dto.SmsSendRequest;
import com.example.tlover.global.sms.dto.SmsSendResponse;
import com.example.tlover.global.sms.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("api/v1/sms")
@RequiredArgsConstructor
@Api(tags = "SMS API")
public class SmsApiController {

    private final SmsService smsService;

    /**
     * 문자 발송 기능입니다.
     * @param smsSendRequest
     * @return
     * @author 한규범
     */
    @ApiOperation(value = "문자발송", notes = "문자를 발송합니다.")
    @PostMapping("/send")
    public ResponseEntity<SmsSendResponse> smsSend(@Valid @RequestBody SmsSendRequest smsSendRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        String value = smsService.sendSms(smsSendRequest);

        return ResponseEntity.ok(SmsSendResponse.builder()
                .value(value)
                .build());
    }

}
