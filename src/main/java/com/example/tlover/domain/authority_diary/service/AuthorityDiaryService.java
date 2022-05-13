package com.example.tlover.domain.authority_diary.service;
import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority_diary.dto.CheckAuthorityDiaryResponse;
import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.diary.entity.Diary;

import javax.validation.Valid;
import java.util.List;

public interface AuthorityDiaryService {

    void addDiaryUser(@Valid Diary diary, String loginId);

    AuthorityDiaryResponse sharePlan(Long diaryId, SharePlanRequest sharePlanRequest);

    AuthorityDiaryResponse updateAcceptAuthorityDiary(Long authorityDiaryId);

    AuthorityDiaryResponse updateRejectAuthorityDiary(Long authorityDiaryId);

    List<AuthorityDiaryListResponse> getListRequestAuthUser(String loginId);

    void getListHostAuthor(String loginId);

    CheckAuthorityDiaryResponse checkAuthorityDiary(String loginId, Long diaryId);
}
