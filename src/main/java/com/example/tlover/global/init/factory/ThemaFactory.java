package com.example.tlover.global.init.factory;

import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.global.init.constant.InitConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThemaFactory {

    public List<Thema> createThemaLists() {
        Thema thema0 = Thema.builder().themaName(InitConstants.EThemaList.ACTIVITY.getValue()).build();
        Thema thema1 = Thema.builder().themaName(InitConstants.EThemaList.HEALING.getValue()).build();
        Thema thema2 = Thema.builder().themaName(InitConstants.EThemaList.FAMOUS_RESTAURANT.getValue()).build();
        Thema thema3 = Thema.builder().themaName(InitConstants.EThemaList.HOT_PLACE.getValue()).build();
        Thema thema4 = Thema.builder().themaName(InitConstants.EThemaList.SPRING_OUTING.getValue()).build();
        Thema thema5 = Thema.builder().themaName(InitConstants.EThemaList.DRIVE.getValue()).build();
        Thema thema6 = Thema.builder().themaName(InitConstants.EThemaList.FAMILY.getValue()).build();
        Thema thema7 = Thema.builder().themaName(InitConstants.EThemaList.FRIEND.getValue()).build();
        Thema thema8 = Thema.builder().themaName(InitConstants.EThemaList.DATE.getValue()).build();
        Thema thema9 = Thema.builder().themaName(InitConstants.EThemaList.EMOTIONAL_TRAVEL.getValue()).build();
        Thema thema10 = Thema.builder().themaName(InitConstants.EThemaList.PHOTO_SPOT.getValue()).build();
        Thema thema11 = Thema.builder().themaName(InitConstants.EThemaList.HISTORICAL_SITE.getValue()).build();

        return List.of(thema0, thema1, thema2, thema3, thema4, thema5, thema6, thema7, thema8, thema9, thema10, thema11);
    }
}
