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
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
import com.example.tlover.domain.diary_region.repository.DiaryRegionRepository;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.diary_thema.repository.DiaryThemaRepository;
import com.example.tlover.domain.diray_liked.entity.DiaryLiked;
import com.example.tlover.domain.diray_liked.repository.DiaryLikedRepository;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.exception.NotFoundMyFileException;
import com.example.tlover.domain.myfile.repository.MyFileRepository;
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
import com.example.tlover.domain.weather.repository.WeatherRepository;
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
import java.util.concurrent.ConcurrentHashMap;
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

    private final MyFileRepository myFileRepository;
    private final RegionRepository regionRepository;
    private final ThemaRepository themaRepository;
    private final AuthorityDiaryService authorityDiaryService;
    private final AuthorityDiaryRepository authorityDiaryRepository;
    private final DiaryLikedRepository diaryLikedRepository;
    private final WeatherRepository weatherRepository;
    private final UserRegionRepository userRegionRepository;
    private final UserThemaRepository userThemaRepository;
    private final WeatherService weatherService;
    private final DiaryConstants diaryConstants;

    @Override
    public CreateDiaryFormResponse getCreateDiaryForm(Long planId, String loginId) {
        Plan plan = planRepository.findByPlanId(planId).orElseThrow(NotFoundPlanException::new);
        String psd = plan.getPlanStartDate();
        String ped = plan.getPlanEndDate();
       return CreateDiaryFormResponse.from( psd, ped,  getPlanDay(psd, ped) , plan.getPlanRegionDetail() , plan.getExpense());
}

    @Override
    public ModifyDiaryFormResponse getModifyDiaryForm(Long diaryId, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).orElseThrow(NotFoundUserException::new);
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);
        List<DiaryThema> byDiary = diaryThemaRepository.findByDiary(diary).orElseThrow(NotFoundDiaryException::new);
        List<MyFile> myFiles = myFileRepository.findByUserAndDiary(user, diary).orElseThrow(NotFoundMyFileException::new);
        List<String> themaNameList = new ArrayList<>();
        Map <Long ,String > myFileSet = new ConcurrentHashMap<>();
        if(diary.getDiaryStatus().equals("DELETE")) throw new NotFoundDiaryException();
        for (DiaryThema diaryThema : byDiary) {
            themaNameList.add(diaryThema.getThema().getThemaName());
        }
        for (MyFile myFile : myFiles) {
            myFileSet.put(myFile.getMyFileId() , myFile.getFileKey());
        }

        return ModifyDiaryFormResponse.from(diary , themaNameList , myFileSet);
    }



    @Override
    @Transactional
    public CreateDiaryResponse createDiary(CreateDiaryRequest createDiaryRequest, String loginId) {

        User user = userRepository.findByUserLoginId(loginId).get();
        Plan plan = planRepository.findByPlanId(createDiaryRequest.getPlanId()).get();
        Optional<Diary> cdr = diaryRepository.findByUserAndPlan(user,plan);

        Long diaryId =0L;
        if(cdr.isEmpty() || cdr.get().getDiaryStatus().equals("DELETE")) {
            String regionDetail = toString(createDiaryRequest.getRegionName());

            Diary diary = diaryRepository.save(Diary.toEntity(regionDetail, createDiaryRequest,user, plan , getPlanDay(plan.getPlanStartDate(), plan.getPlanEndDate())));
            diaryId = diary.getDiaryId();

            authorityDiaryService.addDiaryUser(diary , loginId);
            String[] regions = checkRegion(createDiaryRequest.getRegionName());

            for (String regionName : regions) {
                Region region = regionRepository.findByRegionName(regionName).get();
                DiaryRegion diaryRegion = DiaryRegion.toEntity(region, diary);
                diaryRegionRepository.save(diaryRegion);
            }

            for (String themaName : createDiaryRequest.getThemaName()) {
                Thema thema = themaRepository.findByThemaName(themaName);
                DiaryThema diaryThema = DiaryThema.toEntity(thema, diary);
                diaryThemaRepository.save(diaryThema);
            }

//            for (MultipartFile diaryImgFileName : createDiaryRequest.getDiaryImages()) {
//                MyFile myFile = myFileService.saveImage(diaryImgFileName);
//                myFile.setDiary(diary);
//                myFile.setUser(user);
//            }

            //썸네일 추가
            String fileKey = myFileRepository.findByUserAndDiary(user, diary).get().stream().findFirst().get().getFileKey();
            diary.setDiaryView(fileKey);

        }
        return CreateDiaryResponse.from(diaryId , true);
    }

    private int getPlanDay(String psd , String ped) {
        LocalDateTime startDate = LocalDateTime.parse(psd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(ped, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int result = (endDate.getDayOfMonth() - startDate.getDayOfMonth()) + 1;
        if(result <= 0) throw new NoCorrectDayException();
        return result;
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
        authorityDiaryRepository.deleteByDiary_DiaryId(diaryId);

        return DeleteDiaryResponse.from(diary.getDiaryId() , true);
    }

    @Override
    @Transactional
    public Diary modifyDiary(ModifyDiaryRequest modifyDiaryRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        if(diaryRepository.findByDiaryId(modifyDiaryRequest.getDiaryId())==null) throw new NotFoundDiaryException();

        Diary diary = diaryRepository.findByUserAndDiaryId(user,modifyDiaryRequest.getDiaryId());
        if(diary==null) throw new NoAuthorityModifyException();

        if(diary.getDiaryStatus().equals("EDIT")) throw new AlreadyEditDiaryException();

        diary.setDiaryTitle(modifyDiaryRequest.getDiaryTitle());
        diary.setDiaryStartDate(modifyDiaryRequest.getDiaryStartDate());
        diary.setDiaryEndDate(modifyDiaryRequest.getDiaryEndDate());
        diary.setDiaryWriteDate(LocalDateTime.now().toString());
        diary.setDiaryRegionDetail(modifyDiaryRequest.getRegionNameDetail());

        //사진 수정
        for(int i=0; i<diary.getMyFiles().size(); i++){
            myFileService.deleteFile(diary.getMyFiles().get(i).getMyFileId());
        }

        for (MultipartFile diaryImgFileName : modifyDiaryRequest.getDiaryImages()) {
            MyFile myFile = myFileService.saveImage(diaryImgFileName);
            myFile.setDiary(diary);
            myFile.setUser(user);
        }

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
    public PaginationDto<List<DiaryMyScrapOrLikedResponse>> getDiaryMyLiked(Pageable pageable , Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(NotFoundUserException::new);
        Page<DiaryMyScrapOrLikedResponse> page = diaryRepository.findAllDiariesByMyLiked(pageable , user);
        List<DiaryMyScrapOrLikedResponse> data = page.get().collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }

    @Override
    public PaginationDto<List<DiaryMyScrapOrLikedResponse>> getDiaryMyScrap(Pageable pageable, Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(NotFoundUserException::new);
        Page<DiaryMyScrapOrLikedResponse> page = diaryRepository.findAllDiariesByMyScrap(pageable , user);
        List<DiaryMyScrapOrLikedResponse> data = page.get().collect(Collectors.toList());
        return PaginationDto.of(page, data);
    }


    @Override
    public DiaryLikedViewsResponse getDiaryViews(Long diaryId) {
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);
        Long dlv = diaryLikedRepository.countByDiaryAndIsLiked(diary, true).get();
        return DiaryLikedViewsResponse.from(diary.getDiaryId() , dlv);

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
    public UpdateDiaryStatusResponse updateDiaryEditing(String loginId, Long diaryId) {
        Diary diary = diaryRepository.findByDiaryId(diaryId).orElseThrow(NotFoundDiaryException::new);
        diary.setDiaryStatus("EDIT");
        return UpdateDiaryStatusResponse.from(diary);
    }

    @Override
    public List<DiaryWeatherResponse> getDiaryWeather(String loginId) {
        //결과를 위한 배열
        List<DiaryWeatherResponse> diaryWeatherResponses = new ArrayList<>();

        List<Diary> diaries = diaryRepository.weatherDiary();
        if(diaries.isEmpty()) throw new NotFoundGoodWeather();

        for(Diary diary: diaries){
            diaryWeatherResponses.add(DiaryWeatherResponse.from(diary,
                    diaryRepository.diaryRegions(diary.getDiaryId()),
                    diaryRepository.diaryImg(diary.getDiaryId())));
        }

        Collections.shuffle(diaryWeatherResponses);

        return diaryWeatherResponses;
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
        List<DiaryThema> diaryThemas = diaryThemaRepository.findByDiary(diary).orElseThrow(NotFoundDiaryException::new);
        for (DiaryThema diaryThema : diaryThemas) {
            String diaryThemaName = diaryThema.getThema().getThemaName();
            diaryThemaNames.add(diaryThemaName);
        }
        return diaryThemaNames;
    }
    private String toString(String[] regionName){
        String regionDetail = String.join(", ", regionName);
        return regionDetail;
    }

    private String[] checkRegion(String[] regionName){
        ArrayList<String> regions = new ArrayList<>();
        for(String s : regionName){
            if(s.equals("제주")||s.equals("서귀포")) regions.add("제주도");
            else if(s.equals("춘천")||s.equals("속초")||s.equals("강릉")) regions.add("강원도");
            else if(s.equals("서울")) regions.add("서울");
            else if(s.equals("인천")) regions.add("인천");
            else if(s.equals("양평")||s.equals("가평")||s.equals("파주")) regions.add("경기도");
            else if(s.equals("부산")||s.equals("거제")) regions.add("경상남도");
            else if(s.equals("안동")||s.equals("경주")||s.equals("포항")) regions.add("경상북도");
            else if(s.equals("태안")||s.equals("공주")||s.equals("보령")) regions.add("충청남도");
            else if(s.equals("단양")) regions.add("충청북도");
            else if(s.equals("여수")||s.equals("목포")||s.equals("순천")||s.equals("담양")) regions.add("전라남도");
            else if(s.equals("전주")) regions.add("전라북도");
        }
        HashSet<String> hashSet = new HashSet<>(regions);
        regions=new ArrayList<>(hashSet);
        String[] region = new String[regions.size()];
        for(int i=0; i< regions.size(); i++) {
            region[i] = regions.get(i);
        }
        return region;
    }

}