package com.example.tlover.domain.history.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.history.dto.CreateHistoryRequest;
import com.example.tlover.domain.history.dto.GetHistoryResponse;
import com.example.tlover.domain.history.exception.NotFoundHistoryException;
import com.example.tlover.domain.history.exception.RejectDeletedDiaryException;
import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.history.exception.RejectGetDiaryException;
import com.example.tlover.domain.history.repository.HistoryRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.tlover.domain.history.constant.HistoryConstants.*;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final HistoryRepository historyRepository;

    @Override
    public void createHistory(String loginId, CreateHistoryRequest createHistoryRequest) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Diary diary = diaryRepository.findByDiaryId(createHistoryRequest.getDiaryId())
                .orElseThrow(() -> new NotFoundDiaryException());
        if (diary.getDiaryStatus().equals(EHistory.eComplete.getValue())) {
            History history = historyRepository.findByDiary_DiaryIdAndUser_UserId(diary.getDiaryId(), user.getUserId())
                    .map(entity -> entity.updateEntity(createHistoryRequest.getDate().toString()))
                    .orElse(History.toEntity(createHistoryRequest, user, diary));
            historyRepository.save(history);
        } else if (diary.getDiaryStatus().equals(EHistory.eDelete.getValue())) {
            throw new RejectDeletedDiaryException();
        } else {
            throw new RejectGetDiaryException();
        }
    }

    @Override
    public List<GetHistoryResponse> getUserHistory(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);

        List<History> histories = historyRepository.findByUser(user);
        List<GetHistoryResponse> historyResponses = new ArrayList<>();

        for (History history : histories) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(history.getDate(), formatter);
            int compare = LocalDateTime.now().minusMonths(1).compareTo(dateTime);
            if (compare > 0) {
                historyRepository.deleteByUserAndAndHistoryId(user, history.getHistoryId());
            }
        }
        List<History> newHistories = historyRepository.findByUser(user);
        for (History newHistory : newHistories) {
            historyResponses.add(GetHistoryResponse.from(newHistory, user));
        }

        Collections.sort(historyResponses, new Comparator<GetHistoryResponse>() {
            @Override
            public int compare(GetHistoryResponse o1, GetHistoryResponse o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        if (historyResponses.isEmpty()) {
            throw new NotFoundHistoryException();
        }
        return historyResponses;
    }

    @Override
    public void deleteHistory(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        historyRepository.deleteByUser(user);
    }
}
