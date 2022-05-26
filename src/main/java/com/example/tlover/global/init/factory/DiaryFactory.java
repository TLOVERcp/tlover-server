package com.example.tlover.global.init.factory;

import com.example.tlover.domain.diary.entity.Diary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiaryFactory {

    public List<Diary> createDiaryLists() {
        Diary diary0 = Diary.builder().diaryTitle("혜린이랑 나영이랑 우정 여행").diaryContext("첫 시작이 너무 좋았던 속초 여행 1일차! 일단 날씨가 완벽해서 오션뷰 최고였고, 근처에서 오징어 순대 사먹었는데 대박 맛있었다ㅠㅠ 또 먹고 싶은데 하...따끈따끈한 오징어 순대 계속 생각난다 우짜지ㅠㅠ")
                .totalCost(100000).diaryRegionDetail("강릉, 춘천")
                .diaryPlanDays(1).diaryStartDate("2022-04-05 04:50:05").diaryEndDate("2022-04-09 22:50:05").diaryStatus("ACTIVE").build();
        return List.of(diary0);
    }
}
