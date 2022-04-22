package com.example.tlover.domain.authority_diary.service;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_plan.dto.SharePlanRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void sharePlan(Long diaryId, SharePlanRequest sharePlanRequest) {
        
            Optional<User> optionalUser= userRepository.findByUserNickName(sharePlanRequest.getUserNickName());

            if(optionalUser.isEmpty()) throw new RuntimeException("잘못된 유저입니다.");

            User user = optionalUser.get();

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByUserUserId(user.getUserId());

//        비어있어야 정상임.
        if(cadr.isEmpty()) {

            Diary diary = diaryRepository.findByDiaryId(diaryId);
            AuthorityDiary authorityDiary = AuthorityDiary.toEntity(user, diary, "REQUEST");
            authorityDiaryRepository.save(authorityDiary);
        } else{
            AuthorityDiary authorityDiary = cadr.get();
            if(authorityDiary.getAuthorityDiaryStatus().equals("REQUEST")) throw new RuntimeException("이미 권한 요청을 한 유저입니다.");
            if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT")) throw new RuntimeException("요청을 거절한 유저입니다.");
            else if(authorityDiary.getAuthorityDiaryStatus().equals("HOST")) throw new RuntimeException("이미 호스트 입니다.");
        }
    }

    @Override
    @Transactional
    public void updateAcceptAuthorityDiary(Long authorityDiaryId) {

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId);

        if(cadr.isEmpty()) throw new RuntimeException("존재하지 않는 게시물 입니다.");

        cadr.get().setAuthorityDiaryStatus("ACCEPT");

    }

    @Override
    public void updateRejectAuthorityDiary(Long authorityDiaryId) {
//        AuthorityDiary authorityDiary = authorityDiaryRepository.findByAuthorityDiaryId(authorityDiaryId);
//        authorityDiary.setAuthorityDiaryStatus("REJECT");
    }


}
