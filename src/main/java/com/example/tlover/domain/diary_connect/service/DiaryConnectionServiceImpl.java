package com.example.tlover.domain.diary_connect.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.diary_connect.constant.DiaryConnectionConstants.EDiaryConnectionServiceImpl;
import com.example.tlover.domain.diary_connect.entity.DiaryConnection;
import com.example.tlover.domain.diary_connect.repository.DiaryConnectionConnectionRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaryConnectionServiceImpl implements DiaryConnectionService{

    private final DiaryConnectionConnectionRepository diaryConnectionRepository;
    private final UserService userService;
    private final DiaryService diaryService;

    @Override
    public DiaryInquiryResponse getDiaryDetails(Long diaryId, Long userId) {
        Diary diary = this.diaryService.getDiaryByDiaryId(diaryId);
        User user = this.userService.getUserByUserId(userId);
        Optional<List<DiaryConnection>> findDiaryConnection = this.diaryConnectionRepository.findByDiary_DiaryId(diaryId);
        if(findDiaryConnection.isEmpty()) this.diaryConnectionRepository.save(DiaryConnection.toEntity(diary, user));
        else {
            DiaryConnection diaryConnection = validateCurrentModifiedDateDiaryConnection(findDiaryConnection.get());
            if (diaryConnection != null) diaryConnection.setLastModifiedDate(LocalDateTime.now());
            else this.diaryConnectionRepository.save(DiaryConnection.toEntity(diary, user));
        }
        Integer diaryConnectionCount = this.diaryConnectionRepository.findConnectionCountByDiary(diary).get();
        return DiaryInquiryResponse.from(diary, diaryConnectionCount);
    }


    private DiaryConnection validateCurrentModifiedDateDiaryConnection(List<DiaryConnection> diaryConnectionList) {
        LocalDateTime localDateTime = LocalDateTime.now();
        for (DiaryConnection diaryConnection : diaryConnectionList) {
            LocalDateTime lastModifiedDate = diaryConnection.getLastModifiedDate();
            if (lastModifiedDate.until(localDateTime, ChronoUnit.MINUTES) < EDiaryConnectionServiceImpl.eThirtyMinutes.getValue()) {
                return diaryConnection;
            }
        } return null;
    }
}
