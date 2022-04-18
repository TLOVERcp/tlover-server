package com.example.tlover.domain.scrap.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.scrap.constant.ScrapConstants.EScrapResponseMessage;
import com.example.tlover.domain.scrap.dto.ScrapChangeRequest;
import com.example.tlover.domain.scrap.dto.ScrapChangeResponse;
import com.example.tlover.domain.scrap.dto.ScrapCountResponse;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.scrap.repository.ScrapRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScrapServiceImpl implements ScrapService{

    private final ScrapRepository scrapRepository;
    private final DiaryService diaryService;
    private final UserService userService;

    @Override
    public ScrapCountResponse getScrapCount(Long diaryId) {
        return ScrapCountResponse.from(this.scrapRepository
                .findScrapCountNotDeletedByDiary(this.diaryService.getDiaryByDiaryId(diaryId)).get());
    }

    @Override
    @Transactional
    public ScrapChangeResponse changeScrap(ScrapChangeRequest scrapChangeRequest) {
        User user = this.userService.getUserByUserId(scrapChangeRequest.getUserId());
        Diary diary = this.diaryService.getDiaryByDiaryId(scrapChangeRequest.getDiaryId());
        Optional<Scrap> scrap = this.scrapRepository.findByUserAndDiary(user, diary);
        if(scrap.isEmpty()) {
            this.scrapRepository.save(Scrap.toEntity(user, diary));
            return ScrapChangeResponse.from(scrap.get().getScrapId(), true);}
        else if(scrap.get().isDeleted()) {
            scrap.get().setDeleted(false);
            return ScrapChangeResponse.from(scrap.get().getScrapId(), true); }
        else if(!scrap.get().isDeleted())
            scrap.get().setDeleted(true);
            return ScrapChangeResponse.from(scrap.get().getScrapId(), false);
    }
}
