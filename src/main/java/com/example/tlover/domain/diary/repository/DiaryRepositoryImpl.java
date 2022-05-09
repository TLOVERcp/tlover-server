package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiarySearchResponse;
import com.example.tlover.domain.diary.dto.QDiarySearchResponse;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tlover.domain.diary.entity.QDiary.diary;
import static com.example.tlover.domain.diary_thema.entity.QDiaryThema.diaryThema;
import static com.example.tlover.domain.thema.entity.QThema.thema;
import static com.example.tlover.domain.diary_region.entity.QDiaryRegion.diaryRegion;
import static com.example.tlover.domain.region.entity.QRegion.region;

public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<DiarySearchResponse> findByKeywordCustom(String keyword, Pageable pageable) {

        List<DiarySearchResponse> content = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryPublicStatus,
                        diary.diaryStatus,
                        diary.diaryContext,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView

                ))
                .from(diary)
                .where(diary.diaryTitle.contains(keyword).or(diary.diaryContext.contains(keyword)), diary.diaryStatus.eq("COMPLETE"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiarySearchResponse> countQuery = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryPublicStatus,
                        diary.diaryStatus,
                        diary.diaryContext,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView
                ))
                .from(diary)
                .where(diary.diaryTitle.contains(keyword).or(diary.diaryContext.contains(keyword)), diary.diaryStatus.eq("COMPLETE"));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public Page<DiarySearchResponse> findByThemaKewordCustom(String keyword, Pageable pageable) {

        List<DiarySearchResponse> content = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryPublicStatus,
                        diary.diaryStatus,
                        diary.diaryContext,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView
                ))
                .from(diary)
                .leftJoin(diaryThema)
                .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                .leftJoin(thema)
                .on(diaryThema.thema.themaId.eq(thema.themaId))
                .where(thema.themaName.eq(keyword), diary.diaryStatus.eq("COMPLETE"), diaryThema.diaryThemaId.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiarySearchResponse> countQuery = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryPublicStatus,
                        diary.diaryStatus,
                        diary.diaryContext,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView
                ))
                .from(diary)
                .leftJoin(diaryThema)
                .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                .leftJoin(thema)
                .on(diaryThema.thema.themaId.eq(thema.themaId))
                .where(thema.themaName.eq(keyword), diary.diaryStatus.eq("COMPLETE"), diaryThema.diaryThemaId.isNotNull());

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public List<String> findThemaNamesByDiaryId(Long diaryId) {

        List<String> content = queryFactory
                .select(
                        thema.themaName
                )
                .from(diary)
                .leftJoin(diaryThema)
                .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                .leftJoin(thema)
                .on(diaryThema.thema.themaId.eq(thema.themaId))
                .where(diary.diaryId.eq(diaryId), diary.diaryStatus.eq("COMPLETE"), diaryThema.diaryThemaId.isNotNull())
                .fetch();

        return content;
    }

    @Override
    public List<String> findRegionNamesByDiaryId(Long diaryId) {

        List<String> content = queryFactory
                .select(
                        region.regionName
                )
                .from(diary)
                .leftJoin(diaryRegion)
                .on(diary.diaryId.eq(diaryRegion.diary.diaryId))
                .leftJoin(region)
                .on(diaryRegion.region.regionId.eq(region.regionId))
                .where(diary.diaryId.eq(diaryId), diary.diaryStatus.eq("COMPLETE"), diaryRegion.diaryRegionId.isNotNull())
                .fetch();

        return content;
    }


}
