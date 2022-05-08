package com.example.tlover.domain.history.service;

import com.example.tlover.domain.history.dto.CreateHistoryRequest;
import com.example.tlover.domain.history.dto.GetHistoryResponse;

import java.util.List;

public interface HistoryService {
    void createHistory(String loginId, CreateHistoryRequest createHistoryRequest);
    List<GetHistoryResponse> getUserHistory(String loginId);
    void deleteHistory(String loginId);
}
