package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.scrap.dto.DiaryInquiryByScrapRankingResponse;
import com.example.tlover.domain.scrap.dto.QDiaryInquiryByScrapRankingResponse;
import com.example.tlover.domain.scrap.entity.Scrap;
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
import java.util.Optional;

import static com.example.tlover.domain.diary.entity.QDiary.diary;
import static com.example.tlover.domain.myfile.entity.QMyFile.myFile;
import static com.example.tlover.domain.scrap.entity.QScrap.scrap;

public class ScrapRepositoryImpl implements ScrapRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ScrapRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Long> findScrapCountNotDeletedByDiary(Diary diary) {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .from(scrap)
                .where(diaryEq(diary),
                        isDeletedCheck())
                .fetchCount());
    }

    @Override
    public Optional<Scrap> findByUserAndDiaryAndNotDeleted(User user, Diary diary) {
        return Optional.ofNullable(queryFactory.selectFrom(scrap)
                .from(scrap)
                .where(diaryEq(diary),
                        userEq(user),
                        isDeletedCheck())
                .fetchOne());
    }

    @Override
    public Page<DiaryInquiryByScrapRankingResponse> findAllDiariesByScrapRanking(Pageable pageable) {
        List<DiaryInquiryByScrapRankingResponse> content = queryFactory
                .select(new QDiaryInquiryByScrapRankingResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView,
                        myFile.fileKey,
                        scrapCount.sum()
                ))
                .from(diary)
                .leftJoin(diary.scraps, scrap)
                .leftJoin(diary.myFiles, myFile)
                .orderBy(scrapCount.sum().desc())
                .groupBy(diary.diaryId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiaryInquiryByScrapRankingResponse> countQuery = queryFactory
                .select(new QDiaryInquiryByScrapRankingResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.diaryView,
                        myFile.fileKey,
                        scrapCount.sum()
                ))
                .from(diary)
                .leftJoin(diary.scraps, scrap)
                .leftJoin(diary.myFiles, myFile)
                .orderBy(scrapCount.sum().desc())
                .groupBy(diary.diaryId);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }
    NumberExpression<Long> scrapCount = scrap.isDeleted.
            when(false).then(new Long(1)).
            otherwise(new Long(0));

    private BooleanExpression isDeletedCheck() {return scrap.isDeleted.eq(false);}

    private BooleanExpression diaryEq(Diary diary) {return diary != null ? scrap.diary.eq(diary) : null;}
    private BooleanExpression userEq(User user) {return user != null ? scrap.user.eq(user) : null;}
}
