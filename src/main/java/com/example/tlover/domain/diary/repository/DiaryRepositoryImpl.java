package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiaryInquiryByLikedRankingResponse;
import com.example.tlover.domain.diary.dto.QDiaryInquiryByLikedRankingResponse;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tlover.domain.diary.entity.QDiary.diary;
import static com.example.tlover.domain.diray_liked.entity.QDiaryLiked.*;


public class DiaryRepositoryImpl implements DiaryRepositoryCustom {
        private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DiaryInquiryByLikedRankingResponse> findAllDiariesByLikedRanking(Pageable pageable) {
        List<DiaryInquiryByLikedRankingResponse> content = queryFactory
                .select(new QDiaryInquiryByLikedRankingResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView,
                        likecount.sum()
                ))
                .from(diary)
                .leftJoin(diary.diaryLikeds, diaryLiked)
                .orderBy(likecount.sum().desc())
                .groupBy(diary.diaryId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiaryInquiryByLikedRankingResponse> countQuery = queryFactory
                .select(new QDiaryInquiryByLikedRankingResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView,
                        likecount.sum()
                ))
                .from(diary)
                .leftJoin(diary.diaryLikeds, diaryLiked)
                .orderBy(likecount.sum().desc())
                .groupBy(diary.diaryId);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }


        NumberExpression<Long> likecount = diaryLiked.isLiked.
            when(true).then(new Long(1)).
            otherwise(new Long(0));
}
