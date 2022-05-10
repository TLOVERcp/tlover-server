package com.example.tlover.domain.authority_diary.service;

import com.example.tlover.domain.authority_diary.dto.AuthorityDiaryListResponse;
import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
    public void sharePlan(Long diaryId, SharePlanRequest sharePlanRequest) {

        User user = userRepository.findByUserNickName(sharePlanRequest.getUserNickName()).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByDiaryAndUser(diary , user);

        if(cadr.isEmpty()) {
        authorityDiaryRepository.save(AuthorityDiary.toEntity(user, diary, "REQUEST"));
    } else{

        AuthorityDiary authorityDiary = cadr.get();

            System.out.println(authorityDiary.getAuthorityDiaryId());
            System.out.println(authorityDiary.getAuthorityDiaryStatus());
        if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT")) {
            authorityDiary.setAuthorityDiaryStatus("REQUEST");
            return;
        }
        if(authorityDiary.getAuthorityDiaryStatus().equals("REQUEST")) throw new RuntimeException("이미 권한 요청을 한 유저입니다.");
        if(authorityDiary.getAuthorityDiaryStatus().equals("ACCEPT")) throw new RuntimeException("요청을 수락한 유저입니다.");
        if(authorityDiary.getAuthorityDiaryStatus().equals("HOST")) throw new RuntimeException("이미 호스트 입니다.");
    }
}

    @Override
    @Transactional
    public void updateAcceptAuthorityDiary(Long authorityDiaryId) {
        AuthorityDiary authorityDiary = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId).orElseThrow(NotFoundDiaryException::new);

        if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT")) new RuntimeException("오직 REUEST 일때만 수락이 가능합니다.");

        authorityDiary.setAuthorityDiaryStatus("ACCEPT");
    }


    @Override
    @Transactional
    public void updateRejectAuthorityDiary(Long authorityDiaryId) {
        AuthorityDiary authorityDiary = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId).orElseThrow(NotFoundDiaryException::new);
        authorityDiary.setAuthorityDiaryStatus("REJECT");
    }

    @Override
    public List<AuthorityDiaryListResponse> getListRequestAuthUser(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityDiary> authorityDiaryList = authorityDiaryRepository.findByUserAndAuthorityDiaryStatus(user, "REQUEST").orElseThrow(NotFoundDiaryException::new);

        List<AuthorityDiaryListResponse> adList = new ArrayList<>();

        for (AuthorityDiary authorityDiary : authorityDiaryList) adList.add(AuthorityDiaryListResponse.from(authorityDiary));

        return adList;

    }

    @Override
    public void getListHostAuthor(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);

        List<Diary> diaries = diaryRepository.findByUser(user).orElseThrow();





    }


}
