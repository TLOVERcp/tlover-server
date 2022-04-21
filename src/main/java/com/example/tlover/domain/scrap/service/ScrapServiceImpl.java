package com.example.tlover.domain.scrap.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.scrap.dto.*;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.scrap.repository.ScrapRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.global.dto.PaginationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            Scrap savedScrap = this.scrapRepository.save(Scrap.toEntity(user, diary));
            return ScrapChangeResponse.from(savedScrap.getScrapId(), true);}
        else if(scrap.get().isDeleted()) {
            scrap.get().setDeleted(false);
            return ScrapChangeResponse.from(scrap.get().getScrapId(), true); }
        else if(!scrap.get().isDeleted())
            scrap.get().setDeleted(true);
            return ScrapChangeResponse.from(scrap.get().getScrapId(), false);
    }

    @Override
    public ScrapOrNotResponse getScrapOrNot(ScrapOrNotRequest scrapOrNotRequest) {
        User user = this.userService.getUserByUserId(scrapOrNotRequest.getUserId());
        Diary diary = this.diaryService.getDiaryByDiaryId(scrapOrNotRequest.getDiaryId());
        if(this.scrapRepository.findByUserAndDiaryAndNotDeleted(user, diary).isEmpty()) return ScrapOrNotResponse.from(false);
        else return ScrapOrNotResponse.from(true);
    }

    @Override
    public PaginationDto<List<DiaryInquiryByScrapRankingResponse>> getDiariesByScrapRanking(Pageable pageable) {
        Page<DiaryInquiryByScrapRankingResponse> page = this.scrapRepository.findAllDiariesByScrapRanking(pageable);
        List<DiaryInquiryByScrapRankingResponse> data = page.get().collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }
}
