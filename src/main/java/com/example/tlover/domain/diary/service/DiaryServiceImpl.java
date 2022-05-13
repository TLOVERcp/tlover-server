package com.example.tlover.domain.diary.service;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.authority_diary.repository.AuthorityDiaryRepository;
import com.example.tlover.domain.authority_diary.service.AuthorityDiaryService;
import com.example.tlover.domain.diary.constant.DiaryConstants;
import com.example.tlover.domain.diary.dto.*;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.*;
import com.example.tlover.domain.diary.exception.NoSuchElementException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.diary_context.entity.DiaryContext;
import com.example.tlover.domain.diary_context.repository.DiaryContextRepository;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_region.repository.DiaryRegionRepository;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diary_thema.repository.DiaryThemaRepository;
import com.example.tlover.domain.diray_liked.entity.DiaryLiked;
import com.example.tlover.domain.diray_liked.repository.DiaryLikedRepository;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.plan.exception.NotFoundPlanException;
import com.example.tlover.domain.plan.repository.PlanRepository;
import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.thema.repository.ThemaRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.dto.PaginationDto;
import com.example.tlover.domain.user_region.repository.UserRegionRepository;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import com.example.tlover.domain.user_thema.repository.UserThemaRepository;
import com.example.tlover.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final DiaryRegionRepository diaryRegionRepository;
    private final DiaryThemaRepository diaryThemaRepository;
    private final RegionRepository regionRepository;
    private final ThemaRepository themaRepository;
    private final AuthorityDiaryService authorityDiaryService;
    private final AuthorityDiaryRepository authorityDiaryRepository;
    private final DiaryLikedRepository diaryLikedRepository;

    private final DiaryContextRepository diaryContextRepository;
    private final UserRegionRepository userRegionRepository;
    private final UserThemaRepository userThemaRepository;
    private final WeatherService weatherService;
    private final DiaryConstants diaryConstants;

    @Override
    @Transactional
    public CreateDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

        /*
        다이어리를 생성하는 부분과 작성하는 부분을 나눈다?
        why?
        현재는 처음 다이어리를 생성(createDiary)을할때에 생성한 user가 host로 authority_diary table에서 생성이 된다.
        이후에 다이어리를 일차별로 작성을 하는 과정에서 꼭 user host가 아니여도 권한을 부여받았다면 특정 다이어리에서 작성이 가능해야 하는데
        host가 아닌 user가 권한을 확인하는 과정에서 diary table에서 diaryId를 가져올수있는 방법이 없음 .
        유일하게 가져올 방법은 외부에서 입력을 해주어야하는데 따라서 이미 생성된 다이어리에서 일차별로 작성하는 식으로 구현하면 가능하다
        요약을 하자면 다이어리를 생성하는 부분과 다이어리를 작성하는 부분은 다르게 되어야한다.
         */

        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId()).get();
        Optional<Diary> cdr = diaryRepository.findByUserAndPlan(user,plan);

        Long diaryId =0L;
        if(cdr.isEmpty()) {
            Diary diary = diaryRepository.save(Diary.toEntity(createDiaryRequest, getPlanDay(plan), user, plan));
            diaryId = diary.getDiaryId();
            authorityDiaryService.addDiaryUser(diary , loginId);

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

            saveDiaryImageAndContext(createDiaryRequest, user, diary);

        }

        // 일차별로 구분해야함.
        if(!cdr.isEmpty() && cdr.get().getDiaryStatus().equals(ACTIVE.getValue())) {
            Diary diary = cdr.get();

            AuthorityDiary authorityDiary = authorityDiaryRepository.findByDiaryAndUser(diary, user).orElseThrow(NotFoundDiaryException::new);
            String status = authorityDiary.getAuthorityDiaryStatus();

            if(status.equals("HOST") || status.equals("ACCEPT")) {
                diaryId = diary.getDiaryId();
                saveDiaryImageAndContext(createDiaryRequest, user, diary);
            } else if(status.equals("REJECT")) {
                throw new RuntimeException("작성 권한이 없습니다.");
            } else if(status.equals("REQUEST")) {
                throw new RuntimeException("작성 권한이 없습니다.");
            }
        }

        return CreateDiaryResponse.from(diaryId , true);

    }


    private int getPlanDay(Plan plan) {
        LocalDateTime startDate = LocalDateTime.parse(plan.getPlanStartDate().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endDate = LocalDateTime.parse(plan.getPlanEndDate().toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int result = (endDate.getDayOfMonth() - startDate.getDayOfMonth()) + 1;
        if(result <= 0) throw new RuntimeException("계획 날짜 오류");
        return result;
    }

    private void saveDiaryImageAndContext(CreateDiaryRequest createDiaryRequest, User user, Diary diary) {

        if(createDiaryRequest.getDiaryDay() > diary.getDiaryPlanDays())
            throw new RuntimeException("총 여행일보다 크면 안됩니다.");

        //diaryDay
        Optional<List<MyFile>> cmf = myFileService.findByDiaryAndDiaryDay(diary, createDiaryRequest.getDiaryDay());

        Optional<DiaryContext> cdc =
                diaryContextRepository.findByDiaryAndDiaryDay(diary , createDiaryRequest.getDiaryDay());

        if(cmf.isEmpty() && cdc.isEmpty()) {
            for (MultipartFile diaryImgFileName : createDiaryRequest.getDiaryImages()) {
                MyFile myFile = myFileService.saveImage(diaryImgFileName);
                myFile.setDiaryDay(createDiaryRequest.getDiaryDay());
                myFile.setDiary(diary);
                myFile.setUser(user);
            }
            diaryContextRepository.save(DiaryContext.toEntity(
                    createDiaryRequest.getDiaryContext(),
                    createDiaryRequest.getDiaryDay(),
                    diary));
        } else {
            throw new RuntimeException("해당 날짜에 이미 작성되어있습니다");
        }

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
        diaryThemaRepository.deleteByDiary_DiaryId(diaryId);
        diaryContextRepository.deleteByDiary_DiaryId(diaryId);
        authorityDiaryRepository.deleteByDiary_DiaryId(diaryId);

        return DeleteDiaryResponse.from(diary.getDiaryId() , true);
    }

    @Override
    @Transactional
    public Diary modifyDiary(ModifyDiaryRequest modifyDiaryRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = planRepository.findByPlanId(modifyDiaryRequest.getPlanId()).get();
        Diary diary = diaryRepository.findByUserAndDiaryId(user,modifyDiaryRequest.getDiaryId());

        if(diary.getDiaryStatus().equals("EDIT")) throw new RuntimeException("현재 수정중입니다.");

        diary.setDiaryTitle(modifyDiaryRequest.getDiaryTitle());
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
    @Transactional
    public void completeDiary(String loginId, Long planId, Long diaryId) {

        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByUserAndPlan(user, plan).orElseThrow(NotFoundDiaryException::new);
        diary.setDiaryStatus("COMPLETE");
    }

    @Override
    public  PaginationDto<List<DiaryInquiryByLikedRankingResponse>> getDiaryByLikedRanking(Pageable pageable) {

        Page<DiaryInquiryByLikedRankingResponse> page = diaryRepository.findAllDiariesByLikedRanking(pageable);
        List<DiaryInquiryByLikedRankingResponse> data = page.get().collect(Collectors.toList());
        return PaginationDto.of(page, data);


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
        for (int i = 0; i < diaryThemas.size(); i++) {
            Optional<Diary> diaries = diaryRepository.findByDiaryId(diaryThemas.get(i).getDiary().getDiaryId());
//            if(diaries.get().getDiaryStatus().equals("ACTIVE") || diaries.get().getDiaryStatus().equals("COMPLETE")){
//                diaryInquiryResponseList.add(DiaryInquiryResponse.from(diaries.get()));
//            }
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
            System.out.println( diaryRepository.diaryRegions(diary.getDiaryId()));
            System.out.println( diaryRepository.diaryImg(diary.getDiaryId()));
            diaryPreferenceResponses.add(DiaryPreferenceResponse.from(diary, diaryRepository.diaryRegions(diary.getDiaryId()), diaryRepository.diaryImg(diary.getDiaryId())));
        }

        Collections.shuffle(diaryPreferenceResponses);

        return diaryPreferenceResponses;
    }

    @Override
    public List<MyDiaryListResponse> getDiaryList(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<Diary> diaries = diaryRepository.findByUser(user).orElseThrow(NotFoundDiaryException::new);
        List<MyDiaryListResponse> myDiaryListResponses = new ArrayList<>();
        List<String> diaryRegionNames = new ArrayList<>();
        List<String> diaryThemaNames = new ArrayList<>();

        for (Diary diary : diaries) {
            diaryRegionNames = getDiaryRegions(diaryRegionNames, diary);
            diaryThemaNames = getDiaryThemas(diaryThemaNames, diary);
            myDiaryListResponses.add(MyDiaryListResponse.from(diary, diaryRegionNames, diaryThemaNames));
        }
        if (myDiaryListResponses.isEmpty()) {
            throw new NotFoundMyDiaryException();
        }
        return myDiaryListResponses;
    }

    @Override
    public List<MyDiaryListResponse> getAcceptDiaryList(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        List<AuthorityDiary> acceptDiaries = authorityDiaryRepository.findByAuthorityDiaryStatusAndUser("ACCEPT", user);
        List<MyDiaryListResponse> myDiaryListResponses = new ArrayList<>();
        List<String> diaryRegionNames = new ArrayList<>();
        List<String> diaryThemaNames = new ArrayList<>();

        for (AuthorityDiary acceptDiary : acceptDiaries) {
            diaryRegionNames = getDiaryRegions(diaryRegionNames, acceptDiary.getDiary());
            diaryThemaNames = getDiaryThemas(diaryThemaNames, acceptDiary.getDiary());
            myDiaryListResponses.add(MyDiaryListResponse.from(acceptDiary.getDiary(), diaryRegionNames, diaryThemaNames));
        }
        if (myDiaryListResponses.isEmpty()) {
            throw new NotFoundAcceptDiaryException();
        }
        return myDiaryListResponses;
    }

    @Override
    @Transactional
    public UpdateDiaryStatusResponse updateDiaryEditing(String loginId, Long diaryId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);
        AuthorityDiary authorityDiary = authorityDiaryRepository.findByDiaryAndUser(diary, user).orElseThrow(() -> new RuntimeException("권한이없습니다."));

        if(authorityDiary.getAuthorityDiaryStatus().equals("REQUEST")) throw new RuntimeException("권한이 없습니다.");
        if(authorityDiary.getAuthorityDiaryStatus().equals("REJECT")) throw new RuntimeException("권한이 없습니다.");
        if(diary.getDiaryStatus().equals("EDIT")) throw new RuntimeException("이미 수정중입니다.");

        if(diary.getDiaryStatus().equals("ACTIVE") || diary.getDiaryStatus().equals("COMPLETE")) {
            diary.setDiaryStatus("EDIT");
        }

        return UpdateDiaryStatusResponse.from(diary);

    }

    private List<String> getDiaryRegions(List<String> diaryRegionNames, Diary diary) {
        List<DiaryRegion> diaryRegions = diaryRegionRepository.findByDiary(diary);
        for (DiaryRegion diaryRegion : diaryRegions) {
            String diaryRegionName = diaryRegion.getRegion().getRegionName();
            diaryRegionNames.add(diaryRegionName);
        }
        return diaryRegionNames;
    }

    private List<String> getDiaryThemas(List<String> diaryThemaNames, Diary diary) {
        List<DiaryThema> diaryThemas = diaryThemaRepository.findByDiary(diary);
        for (DiaryThema diaryThema : diaryThemas) {
            String diaryThemaName = diaryThema.getThema().getThemaName();
            diaryThemaNames.add(diaryThemaName);
        }
        return diaryThemaNames;
    }

}