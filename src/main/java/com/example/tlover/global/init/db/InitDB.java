package com.example.tlover.global.init.db;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.init.factory.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitDB {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoConfig;
    private static String createDdlAutoConfig = "create";
    private final UserRepository userRepository;
    private final ThemaRepository themaRepository;
    private final RegionRepository regionRepository;
    private final DiaryRepository diaryRepository;
    private final PlanRepository planRepository;

    private final UserFactory userFactory;
    private final ThemaFactory themaFactory;
    private final RegionFactory regionFactory;
    private final DiaryFactory diaryFactory;
    private final MyFileFactory myFileFactory;
    private final PlanFactory planFactory;

    private final MyFileService myFileService;

    private User user0, user1, user2;
    private Thema thema0, thema1, thema2, thema3, thema4, thema5, thema6, thema7, thema8, thema9, thema10, thema11;
    private Region region0, region1, region2, region3, region4, region5, region6, region7, region8, region9, region10, region11;
    private Diary diary0, diary1, diary2, diary3, diary4, diary5;
    private Plan plan0, plan1;
    private MyFile myFile0, myFile1, myFile2, myFile3, myFile4, myFile5, myFile6, myFile7,
            myFile8, myFile9, myFile10, myFile11, myFile12, myFile13, myFile14;

    @PostConstruct
    public void initDB() throws IOException {
        if(ddlAutoConfig.equals(createDdlAutoConfig)){
            log.info("initialize database");
            initUserDB();
            initThemaDB();
            initRegionDB();
            initMyFile();
            initPlan();
            initDiary();
        }
    }

    private void initPlan() {
        List<Plan> planLists = this.planFactory.createPlanLists();
        this.plan0 = planLists.get(0);
        this.plan0.setUser(this.user0);
        this.planRepository.save(this.plan0);
    }

    private void initMyFile() throws IOException {
        List<MyFile> myFileLists = myFileFactory.createMyFileLists();
        this.myFile0 = myFileLists.get(0);
        this.myFile1 = myFileLists.get(1);
        this.myFile2 = myFileLists.get(2);
        this.myFile3 = myFileLists.get(3);
        this.myFile4 = myFileLists.get(4);
        this.myFile5 = myFileLists.get(5);
        this.myFile6 = myFileLists.get(6);
        this.myFile7 = myFileLists.get(7);
        this.myFile8 = myFileLists.get(8);
        this.myFile9 = myFileLists.get(9);
        this.myFile10 = myFileLists.get(10);
        this.myFile11 = myFileLists.get(11);
        this.myFile12 = myFileLists.get(12);
        this.myFile13 = myFileLists.get(13);
        this.myFile14 = myFileLists.get(14);
    }

    private void initDiary() {
        List<Diary> diaryLists = this.diaryFactory.createDiaryLists();
        this.diary0 = diaryLists.get(0);
        this.diary0.setPlan(this.plan0);
        this.diary0.setUser(this.user0);
        this.diary0.setMyFiles(List.of(this.myFile4, this.myFile5, this.myFile6));
        this.diaryRepository.save(this.diary0);
    }

    private void initRegionDB() {
        List<Region> regionLists = this.regionFactory.createRegionLists();
        this.region0 = regionLists.get(0);
        this.region1 = regionLists.get(1);
        this.region2 = regionLists.get(2);
        this.region3 = regionLists.get(3);
        this.region4 = regionLists.get(4);
        this.region5 = regionLists.get(5);
        this.region6 = regionLists.get(6);
        this.region7 = regionLists.get(7);
        this.region8 = regionLists.get(8);
        this.region9 = regionLists.get(9);
        this.region10 = regionLists.get(10);
        this.region11 = regionLists.get(11);
        this.regionRepository.save(this.region0);
        this.regionRepository.save(this.region1);
        this.regionRepository.save(this.region2);
        this.regionRepository.save(this.region3);
        this.regionRepository.save(this.region4);
        this.regionRepository.save(this.region5);
        this.regionRepository.save(this.region6);
        this.regionRepository.save(this.region7);
        this.regionRepository.save(this.region8);
        this.regionRepository.save(this.region9);
        this.regionRepository.save(this.region10);
        this.regionRepository.save(this.region11);
    }

    private void initThemaDB() {
        List<Thema> themaLists = this.themaFactory.createThemaLists();
        this.thema0 = themaLists.get(0);
        this.thema1 = themaLists.get(1);
        this.thema2 = themaLists.get(2);
        this.thema3 = themaLists.get(3);
        this.thema4 = themaLists.get(4);
        this.thema5 = themaLists.get(5);
        this.thema6 = themaLists.get(6);
        this.thema7 = themaLists.get(7);
        this.thema8 = themaLists.get(8);
        this.thema9 = themaLists.get(9);
        this.thema10 = themaLists.get(10);
        this.thema11 = themaLists.get(11);
        this.themaRepository.save(this.thema0);
        this.themaRepository.save(this.thema1);
        this.themaRepository.save(this.thema2);
        this.themaRepository.save(this.thema3);
        this.themaRepository.save(this.thema4);
        this.themaRepository.save(this.thema5);
        this.themaRepository.save(this.thema6);
        this.themaRepository.save(this.thema7);
        this.themaRepository.save(this.thema8);
        this.themaRepository.save(this.thema9);
        this.themaRepository.save(this.thema10);
        this.themaRepository.save(this.thema11);
    }

    private void initUserDB() {
        List<User> userLists = userFactory.createUserLists();
        this.user0 = userLists.get(0);
        this.user1 = userLists.get(1);
        this.user2 = userLists.get(2);
        this.userRepository.save(this.user0);
        this.userRepository.save(this.user1);
        this.userRepository.save(this.user2);
    }

}
