package com.example.tlover.global.init.factory;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.plan.entity.Plan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlanFactory {

    public List<Plan> createPlanLists() {
        Plan plan0 = Plan.builder().planTitle("혜린이랑 나영이랑 우정 여행").planContext("1일차 - 속초 가서 오징어 순대 \uD83E\uDD91 2일차 - 강릉가서 오션뷰 티타임 ☕ 3일차 - 거제도에서 조개구이 먹으면서 술 한잔")
                .expense(100000L).planRegionDetail("춘천, 속초, 강릉, 제주도, 가평")
                .planStartDate("2022-05-22 09:10:24").planEndDate("2022-05-28 09:10:24").build();
        return List.of(plan0);
    }
}
