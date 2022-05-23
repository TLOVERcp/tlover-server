package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiarySearchResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> , DiaryRepositoryCustom{
    Diary findByUserAndDiaryId(User user, Long diaryId);
    List<Diary> findBy();
    Optional<Diary> findByDiaryId(Long diaryId);
    Optional<Diary> findByUserUserIdAndPlanPlanId(Long userId, Long planId);
    Optional<List<Diary>> findByUser(User user);

    Optional<Diary> findByUserAndPlan(User user, Plan plan);

            List<Diary> findByDiaryThemas(DiaryThema diaryThema);


    @Query(value = "select region_name" +
            " from diary, diary_region, region " +
            " where diary.diary_id = diary_region.diary_diary_id and diary_region.region_region_id = region.region_id and diary.diary_id= :diaryId ",nativeQuery = true)
    List<String> diaryRegions(@Param("diaryId") Long diaryId);

    @Query(value = "select file_key" +
            " from my_file, diary" +
            " where diary.diary_id = my_file.diary_diary_id " +
            " and diary.diary_id= :diaryId", nativeQuery = true)
    List<String> diaryImg(@Param("diaryId") Long diaryId);

    @Query(value = "select *" +
            " from diary,diary_region,weather" +
            " where diary.diary_id = diary_region.diary_diary_id" +
            "  and diary_region.diary_region_id = weather.region_region_id" +
            "  and weather.weather_tci_grade='매우좋음'" +
            " group by diary.diary_id", nativeQuery = true)
    List<Diary> weatherDiary();

    Optional<Diary> findByPlan(Plan plan);

}
