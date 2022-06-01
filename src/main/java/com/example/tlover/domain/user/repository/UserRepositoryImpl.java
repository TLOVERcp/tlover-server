package com.example.tlover.domain.user.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tlover.domain.diary.entity.QDiaryThema.diaryThema;
import static com.example.tlover.domain.thema.entity.QThema.thema;
import static com.example.tlover.domain.region.entity.QRegion.region;
import static com.example.tlover.domain.user.entity.QUser.user;
import static com.example.tlover.domain.user.entity.QUserRegion.userRegion;
import static com.example.tlover.domain.user.entity.QUserThema.userThema;

public class UserRepositoryImpl implements UserRepositoryCustom {
        private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findThemaNamesByUserId(Long userId) {

        List<String> content = queryFactory
                .select(
                        thema.themaName
                )
                .from(user)
                .leftJoin(userThema)
                .on(user.userId.eq(userThema.user.userId))
                .leftJoin(thema)
                .on(diaryThema.thema.themaId.eq(thema.themaId))
                .where(user.userId.eq(userId), diaryThema.diaryThemaId.isNotNull())
                .fetch();
        return content;
    }

    @Override
    public List<String> findRegionByUserId(Long userId) {

        List<String> content = queryFactory
                .select(
                        region.regionName
                )
                .from(user)
                .leftJoin(userRegion)
                .on(user.userId.eq(userRegion.user.userId))
                .leftJoin(region)
                .on(userRegion.region.regionId.eq(region.regionId))
                .where(user.userId.eq(userId), userRegion.userRegionId.isNotNull())
                .fetch();
        return content;
    }
}
