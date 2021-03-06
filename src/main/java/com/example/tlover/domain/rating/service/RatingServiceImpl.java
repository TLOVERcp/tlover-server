package com.example.tlover.domain.rating.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary.repository.DiaryLikedRepository;
import com.example.tlover.domain.rating.dto.RatingGetResponse;
import com.example.tlover.domain.rating.entity.Rating;
import com.example.tlover.domain.rating.exception.NotFoundRatingException;
import com.example.tlover.domain.rating.repository.RatingRepository;
import com.example.tlover.domain.scrap.repository.ScrapRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final ScrapRepository scrapRepository;
    private final DiaryLikedRepository diaryLikedRepository;

    @Override
    public void updateRating(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Rating rating = ratingRepository.findByUser(user).orElseThrow(NotFoundRatingException::new);
        if(rating.getRating()<5){
        Optional<List<Diary>> diaries = diaryRepository.findByUser(user);
        if(diaries.isPresent()) {
            long num = diaries.get().size();
            for (Diary d : diaries.get()) {
                num += scrapRepository.findScrapCountNotDeletedByDiary(d).get();
                num += diaryLikedRepository.countByDiaryAndIsLiked(d, true).get();
            }
            if(num>=50){
                num = 50;
            }
            rating.updateRating(num, rating);
            ratingRepository.save(rating);
        }
        }



    }
    @Override
    public RatingGetResponse getRating(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Rating rating = ratingRepository.findByUser(user).orElseThrow(NotFoundRatingException::new);
        return RatingGetResponse.from(rating);
    }

    @Override
    public void createRating(User user) {
        Rating rating = Rating.toEntity(user);
        ratingRepository.save(rating);
    }




}
