package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiaryInquiryByLikedRankingResponse;
import com.example.tlover.domain.diary.dto.DiaryMyScrapOrLikedResponse;
import com.example.tlover.domain.diary.dto.QDiaryInquiryByLikedRankingResponse;
import com.example.tlover.domain.diary.dto.QDiaryMyScrapOrLikedResponse;
import com.example.tlover.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import static com.example.tlover.domain.scrap.entity.QScrap.scrap;


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

    @Override
    public Page<DiaryMyScrapOrLikedResponse> findAllDiariesByMyLiked(Pageable pageable, User user) {
        List<DiaryMyScrapOrLikedResponse> content = queryFactory.select(new QDiaryMyScrapOrLikedResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryView,
                        diary.diaryStartDate,
                        diary.diaryEndDate,
                        diary.diaryPlanDays,
                        diary.diaryRegionDetail,
                        diary.totalCost
                ))
                .from(diary)
                .leftJoin(diaryLiked)
                .on(diary.diaryId.eq(diaryLiked.diary.diaryId))
                .where(userEqByLiked(user), isLikedCheck())
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiaryMyScrapOrLikedResponse> countQuery = queryFactory.select(new QDiaryMyScrapOrLikedResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryView,
                        diary.diaryStartDate,
                        diary.diaryEndDate,
                        diary.diaryPlanDays,
                        diary.diaryRegionDetail,
                        diary.totalCost
                ))
                .from(diary)
                .leftJoin(diaryLiked)
                .on(diary.diaryId.eq(diaryLiked.diary.diaryId))
                .where(userEqByLiked(user), isLikedCheck())
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset());

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public Page<DiaryMyScrapOrLikedResponse> findAllDiariesByMyScrap(Pageable pageable, User user) {
        List<DiaryMyScrapOrLikedResponse> content = queryFactory.select(new QDiaryMyScrapOrLikedResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryView,
                        diary.diaryStartDate,
                        diary.diaryEndDate,
                        diary.diaryPlanDays,
                        diary.diaryRegionDetail,
                        diary.totalCost
                ))
                .from(diary)
                .leftJoin(scrap)
                .on(diary.diaryId.eq(scrap.diary.diaryId))
                .where(userEqByScrap(user), isDeletedCheckByScrap())
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiaryMyScrapOrLikedResponse> countQuery = queryFactory.select(new QDiaryMyScrapOrLikedResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryView,
                        diary.diaryStartDate,
                        diary.diaryEndDate,
                        diary.diaryPlanDays,
                        diary.diaryRegionDetail,
                        diary.totalCost
                ))
                .from(diary)
                .leftJoin(scrap)
                .on(diary.diaryId.eq(diaryLiked.diary.diaryId))
                .where(userEqByScrap(user), isDeletedCheckByScrap())
                .orderBy(diary.diaryId.desc())
                .offset(pageable.getOffset());


        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    NumberExpression<Long> likecount = diaryLiked.isLiked.
            when(true).then(new Long(1)).
            otherwise(new Long(0));

    private BooleanExpression userEqByLiked(User user) {
            return user != null ? diaryLiked.user.eq(user) : null;
    }

    private BooleanExpression userEqByScrap(User user) {
        return user != null ? scrap.user.eq(user) : null;
    }

    private BooleanExpression isLikedCheck() {
        return diaryLiked.isLiked.eq(true);
    }

    private BooleanExpression isDeletedCheckByScrap() {
        return scrap.isDeleted.eq(false);
    }

}
