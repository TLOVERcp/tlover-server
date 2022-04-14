package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

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

    private BooleanExpression isDeletedCheck() {
        return scrap.isDeleted.eq(false);
    }

    private BooleanExpression diaryEq(Diary diary) {return diary != null ? scrap.diary.eq(diary) : null;}
}
