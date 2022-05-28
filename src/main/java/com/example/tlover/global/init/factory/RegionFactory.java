package com.example.tlover.global.init.factory;

import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.global.init.constant.InitConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegionFactory {

    public List<Region> createRegionLists() {
        Region region0 = Region.builder().regionName(InitConstants.ERegionList.SEOUL.getValue()).build();
        Region region1 = Region.builder().regionName(InitConstants.ERegionList.GYEONGGIDO.getValue()).build();
        Region region2 = Region.builder().regionName(InitConstants.ERegionList.GYEONGSANBUKDO.getValue()).build();
        Region region3 = Region.builder().regionName(InitConstants.ERegionList.GYEONGSANNAMDO.getValue()).build();
        Region region4 = Region.builder().regionName(InitConstants.ERegionList.JEJUDO.getValue()).build();
        Region region5 = Region.builder().regionName(InitConstants.ERegionList.CHUNGCHEONGNAMDO.getValue()).build();
        Region region6 = Region.builder().regionName(InitConstants.ERegionList.CHUNGCHEONGBUKDO.getValue()).build();
        Region region7 = Region.builder().regionName(InitConstants.ERegionList.JEOLLABUKDO.getValue()).build();
        Region region8 = Region.builder().regionName(InitConstants.ERegionList.JEOLLANAMDO.getValue()).build();
        Region region9 = Region.builder().regionName(InitConstants.ERegionList.GANGWONDO.getValue()).build();
        Region region10 = Region.builder().regionName(InitConstants.ERegionList.INCHEON.getValue()).build();
        Region region11 = Region.builder().regionName(InitConstants.ERegionList.ETC.getValue()).build();

        return List.of(region0, region1, region2, region3, region4, region5, region6, region7, region8, region9, region10, region11);
    }
}
