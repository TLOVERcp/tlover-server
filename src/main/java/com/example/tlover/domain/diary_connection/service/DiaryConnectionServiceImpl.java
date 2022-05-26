package com.example.tlover.domain.diary_connection.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NoSuchElementException;
import com.example.tlover.domain.diary.service.DiaryService;
import com.example.tlover.domain.diary_connection.constant.DiaryConnectionConstants.EDiaryConnectionServiceImpl;
import com.example.tlover.domain.diary_connection.entity.DiaryConnection;
import com.example.tlover.domain.diary_connection.repository.DiaryConnectionConnectionRepository;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diary_thema.repository.DiaryThemaRepository;
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
    private final DiaryThemaRepository diaryThemaRepository;
    @Override
    public DiaryInquiryResponse getDiaryDetails(Long diaryId, Long userId) {
        Diary diary = this.diaryService.getDiaryByDiaryId(diaryId);
        User user = this.userService.getUserByUserId(userId);
        List<DiaryThema> diaryThemas = diaryThemaRepository.findAllByDiary(diary).orElseThrow(NoSuchElementException::new);
        Optional<List<DiaryConnection>> findDiaryConnection = this.diaryConnectionRepository.findByDiary_DiaryId(diaryId);
        if(findDiaryConnection.isEmpty()) this.diaryConnectionRepository.save(DiaryConnection.toEntity(diary, user));
        else {
            DiaryConnection diaryConnection = validateCurrentModifiedDateDiaryConnection(findDiaryConnection.get());
            if (diaryConnection != null) diaryConnection.setLastModifiedDate(LocalDateTime.now());
            else this.diaryConnectionRepository.save(DiaryConnection.toEntity(diary, user));
        }
        Integer diaryConnectionCount = this.diaryConnectionRepository.findConnectionCountByDiary(diary).get();
        return DiaryInquiryResponse.from(diaryThemas, diary, diaryConnectionCount);
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
