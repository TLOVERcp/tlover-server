package com.example.tlover.domain.history.dto;

import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "방문 기록 조회를 위한 응답 객체")
public class GetHistoryResponse {
    private Long userId;
    private String userNickName;
    private Long diaryId;
    private String diaryTitle;
    private String diaryContext;
    private Long relatedPlanId;
    private String relatedPlanTitle;
    private String relatedPlanContext;
    private String dateTime;

    public static GetHistoryResponse from(History history, User user) {
        return GetHistoryResponse.builder()
                .userId(user.getUserId())
                .userNickName(user.getUserNickName())
                .diaryId(history.getDiary().getDiaryId())
                .diaryTitle(history.getDiary().getDiaryTitle())
                .relatedPlanId(history.getDiary().getPlan().getPlanId())
                .relatedPlanTitle(history.getDiary().getPlan().getPlanTitle())
                .relatedPlanContext(history.getDiary().getPlan().getPlanContext())
                .dateTime(history.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
