package com.example.tlover.global.init.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class InitConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EThemaList{
        ACTIVITY("액티비티"),
        HEALING("힐링"),
        FAMOUS_RESTAURANT("맛집투어"),
        HOT_PLACE("핫플"),
        SPRING_OUTING("봄나들이"),
        DRIVE("드라이브"),
        FAMILY("가족"),
        FRIEND("친구"),
        DATE("데이트"),
        EMOTIONAL_TRAVEL("감성여행"),
        PHOTO_SPOT("포토스팟"),
        HISTORICAL_SITE("유적지");
        private final String value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ERegionList{
        SEOUL("서울"),
        GYEONGGIDO("경기도"),
        GYEONGSANBUKDO("경상북도"),
        GYEONGSANNAMDO("경상남도"),
        JEJUDO("제주도"),
        CHUNGCHEONGNAMDO("충청남도"),
        CHUNGCHEONGBUKDO("충청북도"),
        JEOLLABUKDO("전라북도"),
        JEOLLANAMDO("전라남도"),
        GANGWONDO("강원도"),
        INCHEON("인천"),
        ETC("기타");
        private final String value;
    }
}
