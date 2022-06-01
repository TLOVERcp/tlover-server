package com.example.tlover.domain.history.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary.entity.DiaryLiked;
import com.example.tlover.domain.diary.repository.DiaryLikedRepository;
import com.example.tlover.domain.history.dto.GetHistoryResponse;
import com.example.tlover.domain.history.exception.NotFoundHistoryException;
import com.example.tlover.domain.history.exception.RejectDeletedDiaryException;
import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.history.exception.RejectGetDiaryException;
import com.example.tlover.domain.history.repository.HistoryRepository;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.scrap.repository.ScrapRepository;
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
    private final DiaryLikedRepository diaryLikedRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public void createHistory(Long diaryId, Long userId) {
        User user = userRepository.findByUserId(userId).get();
        Diary diary = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new NotFoundDiaryException());
        if (diary.getDiaryStatus().equals(EHistory.eComplete.getValue()) ||diary.getDiaryStatus().equals(EHistory.eActive.getValue())) {
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
            boolean isScrap = isScrap(user, newHistory.getDiary());
            boolean isLiked = isLiked(user, newHistory.getDiary());
            historyResponses.add(GetHistoryResponse.from(newHistory, isScrap, isLiked));
        }
        Collections.sort(historyResponses, new Comparator<GetHistoryResponse>() {
            @Override
            public int compare(GetHistoryResponse o1, GetHistoryResponse o2) {
                return o2.getHistoryDate().compareTo(o1.getHistoryDate());
            }
        });
        if (historyResponses.isEmpty()) {
            throw new NotFoundHistoryException();
        }
        return historyResponses;
    }

    private boolean isLiked(User user, Diary diary) {
        Optional<DiaryLiked> diaryLiked = diaryLikedRepository.findByUserAndDiary(user, diary);
        if(diaryLiked.isEmpty()) return false;
        if(diaryLiked.get().isLiked() == true) return true;
        else return false;
    }

    private boolean isScrap(User user, Diary diary) {
        Optional<Scrap> scrap = scrapRepository.findByUserAndDiary(user, diary);
        if (scrap.isEmpty()) return false;
        else return true;
    }

    @Override
    public void deleteHistory(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        historyRepository.deleteByUser(user);
    }
}
