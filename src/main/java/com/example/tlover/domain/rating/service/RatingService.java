package com.example.tlover.domain.rating.service;


import com.example.tlover.domain.rating.dto.RatingGetResponse;
import com.example.tlover.domain.user.entity.User;

public interface RatingService {


    RatingGetResponse getRating(String loginId);

    void createRating(User user);

    void updateRating(String loginId);
}
