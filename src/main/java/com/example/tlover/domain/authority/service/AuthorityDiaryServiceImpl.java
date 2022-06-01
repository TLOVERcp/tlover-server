package com.example.tlover.domain.authority.service;

import com.example.tlover.domain.authority.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority.dto.AuthorityDiaryResponse;
import com.example.tlover.domain.authority.dto.CheckAuthorityDiaryResponse;
import com.example.tlover.domain.authority.entity.AuthorityDiary;
import com.example.tlover.domain.authority.exception.*;
import com.example.tlover.domain.authority.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority.dto.SharePlanRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public AuthorityDiaryResponse sharePlan(Long diaryId, SharePlanRequest sharePlanRequest) {

        User user = userRepository.findByUserNickName(sharePlanRequest.getUserNickName()).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByDiaryAndUser(diary , user);

        if(cadr.isEmpty()) {
            return AuthorityDiaryResponse.from(authorityDiaryRepository.save(AuthorityDiary.toEntity(user, diary, "REQUEST")));
        } else{

        AuthorityDiary authorityDiary = cadr.get();
        if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT")) {
            authorityDiary.setAuthorityDiaryStatus("REQUEST");
            return AuthorityDiaryResponse.from(authorityDiary);
        }
        if(authorityDiary.getAuthorityDiaryStatus().equals("REQUEST")) throw new AlreadyRequestException();
        if(authorityDiary.getAuthorityDiaryStatus().equals("ACCEPT")) throw new AlreadyAcceptException();
        if(authorityDiary.getAuthorityDiaryStatus().equals("HOST")) throw new AlreadyHostException();
    }
        return null;
}
    @Override
    @Transactional
    public AuthorityDiaryResponse updateAcceptAuthorityDiary(Long authorityDiaryId) {
        AuthorityDiary authorityDiary = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId).orElseThrow(NotFoundDiaryException::new);

        if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT"))
            throw new OnlyRequestException();

        authorityDiary.setAuthorityDiaryStatus("ACCEPT");

        return AuthorityDiaryResponse.from(authorityDiary);

    }


    @Override
    @Transactional
    public AuthorityDiaryResponse updateRejectAuthorityDiary(Long authorityDiaryId) {
        AuthorityDiary authorityDiary = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId).orElseThrow(NotFoundDiaryException::new);
        authorityDiary.setAuthorityDiaryStatus("REJECT");
        return AuthorityDiaryResponse.from(authorityDiary);
    }

    @Override
    public List<AuthorityDiaryListResponse> getListRequestAuthUser(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityDiary> authorityDiaryList = authorityDiaryRepository.findByUserAndAuthorityDiaryStatus(user, "REQUEST").orElseThrow(NotFoundAuthorityExpception::new);

        List<AuthorityDiaryListResponse> adList = new ArrayList<>();

        for (AuthorityDiary authorityDiary : authorityDiaryList) adList.add(AuthorityDiaryListResponse.from(authorityDiary));

        return adList;

    }

    @Override
    public void getListHostAuthor(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<Diary> diaries = diaryRepository.findByUser(user).orElseThrow(NotFoundUserException::new);

        List<List<AuthorityDiary>> result = new ArrayList<>();

//        for (Diary diary : diaries) {
//            List<AuthorityDiary> authorityDiaryList = authorityDiaryRepository.findByDiaryAndNotAuthorityDiaryStatus(diary, "HOST").orElseThrow(NotFoundAuthorityExpception::new);
//            result.add(authorityDiaryList);
//
//        }
    }

    @Override
    public CheckAuthorityDiaryResponse checkAuthorityDiary(String loginId, Long diaryId) {

        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        AuthorityDiary authorityDiary = authorityDiaryRepository.findByDiaryAndUser(diary, user).orElseThrow(NotFoundAuthorityExpception::new);

        String status = authorityDiary.getAuthorityDiaryStatus();

        if(status.equals("REQUEST") || status.equals("REJECT")) throw new NotFoundAuthorityExpception();

        return CheckAuthorityDiaryResponse.from(authorityDiary);

    }


}
