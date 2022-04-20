package com.example.tlover.domain.authority_diary.service;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.authority_plan.entity.AuthorityPlan;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorityDiaryServiceImpl implements AuthorityDiaryService{

    private final AuthorityDiaryRepository authorityDiaryRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;


    public void addDiaryUser(Diary diary, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        AuthorityDiary authorityDiary = AuthorityDiary.toEntity(user, diary, "HOST");
        authorityDiaryRepository.save(authorityDiary);

    }

    @Override
    public void sharePlan(Long diaryId, SharePlanRequest sharePlanRequest) {
        User user = userRepository.findByUserNickName(sharePlanRequest.getUserNickName()).get();
        Diary diary = diaryRepository.findByDiaryId(diaryId);
        AuthorityDiary authorityDiary = AuthorityDiary.toEntity(user, diary, "REQUEST");
        authorityDiaryRepository.save(authorityDiary);
    }

}
