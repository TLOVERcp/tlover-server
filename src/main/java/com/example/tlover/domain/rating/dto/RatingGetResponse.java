package com.example.tlover.domain.rating.dto;

import com.example.tlover.domain.rating.entity.Rating;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "등급 조회를 위한 응답 객체")
public class RatingGetResponse {
    private Long ratingId;
    private String userLoginId;
    private float rating;

    public static RatingGetResponse from(Rating rating) {
        return RatingGetResponse.builder()
                .ratingId(rating.getRatingId())
                .userLoginId(rating.getUser().getUserLoginId())
                .rating(rating.getRating())
                .build();
    }
}
