package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.diary.constant.DiaryConstants;
import com.example.tlover.domain.diary.dto.CreateDiaryRequest;
import com.example.tlover.domain.diary.dto.DiaryInquiryResponse;
import com.example.tlover.domain.diary.dto.ModifyDiaryRequest;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.AlreadyExistDiaryException;
import com.example.tlover.domain.diary.exception.NotAuthorityDelete;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import com.example.tlover.domain.diary_img.repository.DiaryImgRepository;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_region.repository.DiaryRegionRepository;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diary_thema.repository.DiaryThemaRepository;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.tlover.domain.diary.constant.DiaryConstants.eDiary.*;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final MyFileService myFileService;
    private final DiaryImgRepository diaryImgRepository;
    private final DiaryRegionRepository diaryRegionRepository;
    private final DiaryThemaRepository diaryThemaRepository;
    private final RegionRepository regionRepository;
    private final ThemaRepository themaRepository;
    private final AuthorityDiaryService authorityDiaryService;
    private final AuthorityDiaryRepository authorityDiaryRepository;

    private final DiaryConstants diaryConstants;

    @Override
    public Diary createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

            User user = userRepository.findByUserLoginId(loginId).get();
            Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId()).get();

        Optional<Diary> cdr = diaryRepository.findByUserUserIdAndPlanPlanId(user.getUserId(), plan.getPlanId());
        if(!cdr.isEmpty() && cdr.get().getDiaryStatus().equals(ACTIVE.getValue())) throw new AlreadyExistDiaryException();

        Diary diary = diaryRepository.save(Diary.toEntity(createDiaryRequest, user, plan));
            authorityDiaryService.addDiaryUser(diary , loginId);

        for (MultipartFile diaryImgFileName : createDiaryRequest.getDiaryImages()) {
            DiaryImg diaryImg = DiaryImg.toEntity(myFileService.saveImage(diaryImgFileName).getFileKey(), diary);
            diaryImgRepository.save(diaryImg);
        }

        for (String regionName : createDiaryRequest.getRegionName()) {
            Region region = regionRepository.findByRegionName(regionName).get();
            DiaryRegion diaryRegion = DiaryRegion.toEntity(region, diary);
            diaryRegionRepository.save(diaryRegion);
        }

        for (String themaName : createDiaryRequest.getThemaName()) {
            Thema thema = themaRepository.findByThemaName(themaName);
            DiaryThema diaryThema = DiaryThema.toEntity(thema, diary);
            diaryThemaRepository.save(diaryThema);
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
        Diary diary = diaryRepository.findByDiaryId(diaryId);

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByUserUserIdAndDiaryDiaryId(user.getUserId(), diaryId);
        if(cadr.isEmpty()) {
            throw new NotAuthorityDelete();
        }else {
            if(!cadr.get().getAuthorityDiaryStatus().equals(HOST.getValue()))
                throw new NotAuthorityDelete();
        }

        diary.setDiaryStatus("DELETE");

        diaryRegionRepository.deleteByDiary_DiaryId(diaryId);
        diaryImgRepository.deleteByDiary_DiaryId(diaryId);
        diaryThemaRepository.deleteByDiary_DiaryId(diaryId);
        authorityDiaryRepository.deleteByDiary_DiaryId(diaryId);

        return diary;
    }

    @Override
    @Transactional
    public Diary modifyDiary(ModifyDiaryRequest modifyDiaryRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = planRepository.findByPlanId(modifyDiaryRequest.getPlanId()).get();
        Diary diary = diaryRepository.findByUserAndDiaryId(user,modifyDiaryRequest.getDiaryId());

        diary.setDiaryTitle(modifyDiaryRequest.getDiaryTitle());
        diary.setDiaryContext(modifyDiaryRequest.getDiaryContext());
        diary.setDiaryStartDate(modifyDiaryRequest.getDiaryStartDate().toString());
        diary.setDiaryEndDate(modifyDiaryRequest.getDiaryEndDate().toString());
        diary.setDiaryWriteDate(LocalDateTime.now().toString());
        diary.setPlan(plan);

        return diary;
    }

    @Override
    public Diary getDiaryByDiaryId(Long diaryId) {
        return this.diaryRepository.findById(diaryId).orElseThrow(NotFoundDiaryException::new);
    }

    @Override
    public List<DiaryInquiryResponse> getGoingDiary() {
        Thema thema = themaRepository.findByThemaName("봄나들이");
        DiaryThema diaryThemas = diaryThemaRepository.findByThema(thema);
        List<Diary> diaries = diaryRepository.findAll();
//        thema.get
//        List<Diary> diaries = diaryRepository.findBy();
        List<DiaryInquiryResponse> diaryInquiryResponseList = new ArrayList<>();

        for(Diary d : diaries){
            diaryInquiryResponseList.add(DiaryInquiryResponse.from(d));
        }

        return diaryInquiryResponseList;
    }


}

