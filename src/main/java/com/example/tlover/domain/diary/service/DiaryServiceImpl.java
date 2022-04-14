package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import com.example.tlover.domain.diary_img.repository.DiaryImgRepository;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_region.repository.DiaryRegionRepository;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final MyFileService myFileService;
    private final DiaryImgRepository diaryImgRepository;
    private final DiaryRegionRepository diaryRegionRepository;
    private final RegionRepository regionRepository;

    @Override
    public Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

            User user = userRepository.findByUserLoginId(loginId).get();
            Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId()).get();
            Diary diary = diaryRepository.save(Diary.toEntity(createDiaryRequest, user, plan));

            for(MultipartFile diaryImgFileName :createDiaryRequest.getDiaryImages()) {
                DiaryImg diaryImg = DiaryImg.toEntity(diaryImgFileName.getOriginalFilename(), diary);
                diaryImgRepository.save(diaryImg);
            }

            for (String regionName : createDiaryRequest.getRegionName()) {
                Region region = regionRepository.findByRegionName(regionName).get();
                DiaryRegion diaryRegion = DiaryRegion.toEntity(region, diary);
                diaryRegionRepository.save(diaryRegion);
            }

            return diary;

    }

    @Override
    public List<DiaryInquiryResponse> getDiary() {
        List<Diary> diaries = diaryRepository.findBy();
        List<DiaryInquiryResponse> diaryInquiryResponseList = new ArrayList<>();
        for(Diary d : diaries){
            diaryInquiryResponseList.add(DiaryInquiryResponse.from(d));
        }
        return diaryInquiryResponseList;
    }

    @Override
    @Transactional
    public Diary deleteDiary(Long diaryId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Diary diary = diaryRepository.findByUserAndDiaryId(user, diaryId);
        diary.setDiaryStatus("DELETE");

        diaryRegionRepository.deleteByDiary_DiaryId(diaryId);
        diaryImgRepository.deleteByDiary_DiaryId(diaryId);

        return diary;
    }

    @Override
    public Diary getDiaryByDiaryId(Long diaryId) {
        return this.diaryRepository.findById(diaryId).orElseThrow(NotFoundDiaryException::new);
    }

}

