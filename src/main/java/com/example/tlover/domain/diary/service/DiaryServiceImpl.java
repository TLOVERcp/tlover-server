package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.diary.constant.DiaryConstants;
import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.*;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary_img.entity.DiaryImg;
import com.example.tlover.domain.diary_img.repository.DiaryImgRepository;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_region.repository.DiaryRegionRepository;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diary_thema.repository.DiaryThemaRepository;
import com.example.tlover.domain.diray_liked.entity.DiaryLiked;
import com.example.tlover.domain.diray_liked.repository.DiaryLikedRepository;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.domain.user_region.repository.UserRegionRepository;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import com.example.tlover.domain.user_thema.repository.UserThemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final DiaryLikedRepository diaryLikedRepository;
    private final UserRegionRepository userRegionRepository;
    private final UserThemaRepository userThemaRepository;

    private final DiaryConstants diaryConstants;

    @Override
    @Transactional
    public CreateDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

            User user = userRepository.findByUserLoginId(loginId).get();
            Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId()).get();

        Optional<Diary> cdr = diaryRepository.findByUserUserIdAndPlanPlanId(user.getUserId(), plan.getPlanId());

        if(!cdr.isEmpty() && cdr.get().getDiaryStatus().equals(ACTIVE.getValue())) throw new AlreadyExistDiaryException();
        //의논하기
//        else if(!cdr.isEmpty() && cdr.get().getDiaryStatus().equals(DELETE.getValue())) {
//            diaryRepository.delete(cdr.get());
//        }

        Diary diary = diaryRepository.save(Diary.toEntity(createDiaryRequest, user, plan));
            authorityDiaryService.addDiaryUser(diary , loginId);

        for (MultipartFile diaryImgFileName : createDiaryRequest.getDiaryImages()) {
            MyFile myFile = myFileService.saveImage(diaryImgFileName);
            myFile.setDiary(diary);
            myFile.setUser(user);
            DiaryImg diaryImg = DiaryImg.toEntity(myFile.getFileKey(), diary);
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

        return CreateDiaryResponse.from(diary.getDiaryId() , true);

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
    public DeleteDiaryResponse deleteDiary(Long diaryId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        Optional<AuthorityDiary> cadr = authorityDiaryRepository.findByUserUserIdAndDiaryDiaryId(user.getUserId(), diaryId);

        if(cadr.isEmpty()) {
            throw new NotAuthorityDeleteException();
        }else {
            if(!cadr.get().getAuthorityDiaryStatus().equals(HOST.getValue()))
                throw new NotAuthorityDeleteException();
        }

        diary.setDiaryStatus("DELETE");

        for(MyFile myFile :   myFileService.findByUserAndDiary(user, diary)) {
            myFile.setDeleted(true);
        }

        diaryRegionRepository.deleteByDiary_DiaryId(diaryId);
        diaryImgRepository.deleteByDiary_DiaryId(diaryId);
        diaryThemaRepository.deleteByDiary_DiaryId(diaryId);
        authorityDiaryRepository.deleteByDiary_DiaryId(diaryId);

        return DeleteDiaryResponse.from(diary.getDiaryId() , true);
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
        return this.diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);
    }



    @Override
    @Transactional
    public DiaryLikedChangeResponse diaryLikedChange(Long diaryId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        Optional<DiaryLiked> diaryLiked = diaryLikedRepository.findByUserAndDiary(user, diary);

        if(diaryLiked.isEmpty()) {
            DiaryLiked dl = diaryLikedRepository.save(DiaryLiked.toEntity(user, diary));
            return DiaryLikedChangeResponse.from(dl.getDiaryLikedId() , true);
        }
        else if(diaryLiked.isPresent() && diaryLiked.get().isLiked()) {
            diaryLiked.get().setLiked(false);
            return DiaryLikedChangeResponse.from(diaryLiked.get().getDiaryLikedId() , false);
        }
        else if(diaryLiked.isPresent() && !diaryLiked.get().isLiked()) {
            diaryLiked.get().setLiked(true);
            return DiaryLikedChangeResponse.from(diaryLiked.get().getDiaryLikedId() , true);
        }

        throw new NoSuchElementException();
    }




    @Override
    public DiaryLikedViewsResponse getDiaryViews(Long diaryId) {

        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);

        Long dlv = diaryLikedRepository.countByDiaryAndIsLiked(diary, true).get();

        return DiaryLikedViewsResponse.from(diary.getDiaryId() , dlv);

    }


    @Override
    public List<DiaryInquiryResponse> getGoingDiary() {
        Thema thema = themaRepository.findByThemaName("봄나들이");

        List<DiaryThema> diaryThemas = diaryThemaRepository.findByThema(thema);
        List<DiaryInquiryResponse> diaryInquiryResponseList = new ArrayList<>();

        User u = diaryThemas.get(0).getDiary().getUser();
        System.out.println(u.getUserId());
        for(int i=0; i<diaryThemas.size(); i++){
            Optional<Diary> diaries = diaryRepository.findByDiaryId(diaryThemas.get(i).getDiary().getDiaryId());
            if(diaries.get().getDiaryStatus().equals("ACTIVE") || diaries.get().getDiaryStatus().equals("COMPLETE")){
                diaryInquiryResponseList.add(DiaryInquiryResponse.from(diaries.get()));
            }
        }
        return diaryInquiryResponseList;
    }

    @Override
    public List<DiaryPreferenceResponse> getDiaryPreference(String loginId) {
        //결과를 위한 배열
        List<DiaryPreferenceResponse> diaryPreferenceResponses = new ArrayList<>();
        //유저 정보 가져와
        User user = userRepository.findByUserLoginId(loginId).get();
        //유저 테마 가져와
        List<UserThema> userThemas = user.getUserThemas();

        Optional<Thema> thema = themaRepository.findByThemaId(userThemas.get(0).getThema().getThemaId());

        List<DiaryThema> diaryThemas = diaryThemaRepository.findByThema(thema.get());

        for (int i = 0; i < diaryThemas.size(); i++) {
            Diary diary = diaryThemas.get(i).getDiary();

            diaryPreferenceResponses.add(DiaryPreferenceResponse.from(diary, diaryRepository.diaryRegions(diary.getDiaryId()), diaryRepository.diaryImg(diary.getDiaryId())));
        }

        return diaryPreferenceResponses;
    }

    @Override
    public List<MyDiaryListResponse> getDiaryList(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        List<Diary> diaries = diaryRepository.findByUser(user).get();
        List<MyDiaryListResponse> myDiaryListResponses = new ArrayList<>();
        List<String> diaryRegionNames = new ArrayList<>();
        List<String> diaryThemaNames = new ArrayList<>();

        for (Diary diary : diaries) {
            List<DiaryRegion> diaryRegions = diaryRegionRepository.findByDiary(diary);
            for (DiaryRegion diaryRegion : diaryRegions) {
                String diaryRegionName = diaryRegion.getRegion().getRegionName();
                diaryRegionNames.add(diaryRegionName);
            }
            List<DiaryThema> diaryThemas = diaryThemaRepository.findByDiary(diary);
            for (DiaryThema diaryThema : diaryThemas) {
                String diaryThemaName = diaryThema.getThema().getThemaName();
                diaryThemaNames.add(diaryThemaName);
            }
            myDiaryListResponses.add(MyDiaryListResponse.from(diary, diaryRegionNames, diaryThemaNames));
        }
        return myDiaryListResponses;
    }

    /**
     * 다이어리 검색 조회
     * @return PaginationDto<List<DiarySearchResponse>>
     * @author 윤여찬
     */
    @Override
    public PaginationDto<List<DiarySearchResponse>> getSearchedDiary(String keyword, Pageable pageable) {

        Page<DiarySearchResponse> page;
        Thema thema = themaRepository.findByThemaName(keyword);

        // 키워드가 테마이름인지 확인
        if (thema != null) {
            page = this.diaryRepository.findByThemaKewordCustom(keyword, pageable);
        } else {
            page = this.diaryRepository.findByKeywordCustom(keyword, pageable);
        }

        List<DiarySearchResponse> data = page.get().collect(Collectors.toList());

        for (DiarySearchResponse diary : data) {
            List<String> themaNames = diaryRepository.findThemaNamesByDiaryId(diary.getDiaryId());
            List<String> regionNames = diaryRepository.findRegionNamesByDiaryId(diary.getDiaryId());
            if (themaNames != null) diary.setThemaNames(themaNames);
            if (regionNames != null) diary.setRegionNames(regionNames);
        }

        if (data.isEmpty()) throw new NotFoundSearchDiaryException();

        return PaginationDto.of(page, data);
    }
}