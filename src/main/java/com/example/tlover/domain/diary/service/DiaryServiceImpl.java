package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Override
    public Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

//        User user = userRepository.findByUserLoginId(loginId).get();
//        Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId());
//        Diary diary = Diary.toEntity(createDiaryRequest, user, plan);
//        diaryRepository.save(diary);
//        return diary;
        return null;

    }
}
