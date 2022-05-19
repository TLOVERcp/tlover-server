package com.example.tlover.domain.history.service;

import com.example.tlover.domain.history.dto.GetHistoryResponse;

import java.util.List;

public interface HistoryService {
    void createHistory(Long diaryId, Long userId);
    List<GetHistoryResponse> getUserHistory(String loginId);
    void deleteHistory(String loginId);
}
