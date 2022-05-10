package com.example.tlover.domain.authority_diary.service;
import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.diary.entity.Diary;

import javax.validation.Valid;
import java.util.List;

public interface AuthorityDiaryService {

    void addDiaryUser(@Valid Diary diary, String loginId);

    void sharePlan(Long diaryId, SharePlanRequest sharePlanRequest);

    void updateAcceptAuthorityDiary(Long authorityDiaryId);

    void updateRejectAuthorityDiary(Long authorityDiaryId);

    List<AuthorityDiaryListResponse> getListRequestAuthUser(String loginId);

    void getListHostAuthor(String loginId);
}
