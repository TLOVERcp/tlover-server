package com.example.tlover.domain.history.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
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
import java.util.*;

import static com.example.tlover.domain.history.constant.HistoryConstants.*;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService{

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final HistoryRepository historyRepository;

    @Override
    public void createHistory(Long diaryId, Long userId) {
        User user = userRepository.findByUserId(userId).get();
        Diary diary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new NotFoundDiaryException());
        if (diary.getDiaryStatus().equals(EHistory.eComplete.getValue())) {
            Optional<History> history = historyRepository.findByDiaryAndUser(diary, user);
            if (history.isEmpty()) {
                historyRepository.save(History.toEntity(user, diary, LocalDateTime.now()));
            } else {
                historyRepository.deleteByHistoryId(history.get().getHistoryId());
                historyRepository.save(History.toEntity(user, diary, LocalDateTime.now()));
            }
        } else if (diary.getDiaryStatus().equals(EHistory.eDelete.getValue())) {
            throw new RejectDeletedDiaryException();
        }else {
            throw new RejectGetDiaryException();
        }
    }

    @Override
    public List<GetHistoryResponse> getUserHistory(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);

        List<History> histories = historyRepository.findByUser(user);
        List<GetHistoryResponse> historyResponses = new ArrayList<>();

        for (History history : histories) {
            int compare = LocalDateTime.now().minusMonths(1).compareTo(history.getDate());
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
                return o2.getDateTime().compareTo(o1.getDateTime());
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
