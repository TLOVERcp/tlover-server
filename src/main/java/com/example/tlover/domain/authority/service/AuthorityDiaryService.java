package com.example.tlover.domain.authority.service;
import com.example.tlover.domain.authority.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority.dto.CheckAuthorityDiaryResponse;
import com.example.tlover.domain.authority.dto.SharePlanRequest;
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
