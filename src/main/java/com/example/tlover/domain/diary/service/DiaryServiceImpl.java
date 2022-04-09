package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;

    @Override
    public List<DiaryInquiryResponse> getDiary() {
        List<Diary> diaries = diaryRepository.findBy();
        List<DiaryInquiryResponse> diaryInquiryResponseList = new ArrayList<>();
        for(Diary d : diaries){
            diaryInquiryResponseList.add(DiaryInquiryResponse.from(d));
        }
        return diaryInquiryResponseList;
    }
}
