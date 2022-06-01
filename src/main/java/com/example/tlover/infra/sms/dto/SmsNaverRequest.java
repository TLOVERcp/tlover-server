package com.example.tlover.infra.sms.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "네이버에서 원하는 정보를 담기 위한 객체")
public class SmsNaverRequest {
    private String type;
    private String from;
    private String Content;
    private List<MessageModel> messages;
}
