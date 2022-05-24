package com.example.tlover.global.search.repository;

import com.example.tlover.domain.diary.constant.DiaryConstants;
import com.example.tlover.global.search.dto.DiarySearchResponse;
import com.example.tlover.global.search.dto.QDiarySearchResponse;
import com.example.tlover.global.search.dto.QUserSearchResponse;
import com.example.tlover.global.search.dto.UserSearchResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tlover.domain.diary.entity.QDiary.diary;
import static com.example.tlover.domain.diary_context.entity.QDiaryContext.diaryContext;
import static com.example.tlover.domain.diary_thema.entity.QDiaryThema.diaryThema;
import static com.example.tlover.domain.thema.entity.QThema.thema;
import static com.example.tlover.domain.user.entity.QUser.user;
import static com.example.tlover.domain.myfile.entity.QMyFile.myFile;

public class SearchRepositoryImpl implements SearchRepositoryCustom {
        private final JPAQueryFactory queryFactory;
        private final String diaryStatus = DiaryConstants.eDiary.COMPLETE.getValue();

    public SearchRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

        @Override
        public Page<DiarySearchResponse> findDiaryByThema(String keyword, Pageable pageable) {

            List<DiarySearchResponse> content = queryFactory
                    .select(new QDiarySearchResponse(
                            diary.diaryId,
                            diary.diaryTitle,
                            diary.diaryStatus,
                            diary.diaryStartDate,
                            diary.diaryWriteDate,
                            diary.diaryEndDate,
                            diary.totalCost
                    ))
                    .from(diary)
                    .leftJoin(diaryThema)
                    .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                    .leftJoin(thema)
                    .on(diaryThema.thema.themaId.eq(thema.themaId))
                    .where(thema.themaName.eq(keyword), diary.diaryStatus.eq(diaryStatus), diaryThema.diaryThemaId.isNotNull())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            JPAQuery<DiarySearchResponse> countQuery = queryFactory
                    .select(new QDiarySearchResponse(
                            diary.diaryId,
                            diary.diaryTitle,
                            diary.diaryStatus,
                            diary.diaryStartDate,
                            diary.diaryWriteDate,
                            diary.diaryEndDate,
                            diary.totalCost
                    ))
                    .from(diary)
                    .leftJoin(diaryThema)
                    .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                    .leftJoin(thema)
                    .on(diaryThema.thema.themaId.eq(thema.themaId))
                    .where(thema.themaName.eq(keyword), diary.diaryStatus.eq(diaryStatus), diaryThema.diaryThemaId.isNotNull());

            return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());


        }

    @Override
    public Page<DiarySearchResponse> findDiaryByRegion(String keyword, Pageable pageable) {

        List<DiarySearchResponse> content = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.totalCost
                ))
                .from(diary)
                .where(diary.diaryRegionDetail.contains(keyword), diary.diaryStatus.eq(diaryStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<DiarySearchResponse> countQuery = queryFactory
                .select(new QDiarySearchResponse(
                        diary.diaryId,
                        diary.diaryTitle,
                        diary.diaryStatus,
                        diary.diaryStartDate,
                        diary.diaryWriteDate,
                        diary.diaryEndDate,
                        diary.totalCost
                ))
                .from(diary)
                .where(diary.diaryRegionDetail.contains(keyword), diary.diaryStatus.eq(diaryStatus));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());


    }

        @Override
        public Page<DiarySearchResponse> findDiaryByKeyword(String keyword, Pageable pageable) {
            List<DiarySearchResponse> content = queryFactory
                    .select(new QDiarySearchResponse(
                            diary.diaryId,
                            diary.diaryTitle,
                            diary.diaryStatus,
                            diary.diaryStartDate,
                            diary.diaryWriteDate,
                            diary.diaryEndDate,
                            diary.totalCost
                    ))
                    .from(diary)
                    .leftJoin(diaryContext)
                    .on(diary.diaryId.eq(diaryContext.diary.diaryId))
                    .where(diary.diaryTitle.contains(keyword)
                            .or(diaryContext.context.contains(keyword)), diary.diaryStatus.eq(diaryStatus))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            JPAQuery<DiarySearchResponse> countQuery = queryFactory
                    .select(new QDiarySearchResponse(
                            diary.diaryId,
                            diary.diaryTitle,
                            diary.diaryStatus,
                            diary.diaryStartDate,
                            diary.diaryWriteDate,
                            diary.diaryEndDate,
                            diary.totalCost
                    ))
                    .from(diary)
                    .leftJoin(diaryContext)
                    .on(diary.diaryId.eq(diaryContext.diary.diaryId))
                    .where(diary.diaryTitle.contains(keyword)
                            .or(diaryContext.context.contains(keyword)), diary.diaryStatus.eq(diaryStatus));

            return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
        }

        @Override
        public List<String> findThemaNamesByDiaryId(Long diaryId) {
            List<String> content = queryFactory
                    .select(thema.themaName
                    )
                    .from(diary)
                    .leftJoin(diaryThema)
                    .on(diary.diaryId.eq(diaryThema.diary.diaryId))
                    .leftJoin(thema)
                    .on(diaryThema.thema.themaId.eq(thema.themaId))
                    .where(diary.diaryId.eq(diaryId), diary.diaryStatus.eq(diaryStatus), diaryThema.diaryThemaId.isNotNull())
                    .fetch();

            return content;
        }

        @Override
        public List<String> findRegionDetailsByDiaryId(Long diaryId) {
            List<String> content = queryFactory
                    .select(
                            diary.diaryRegionDetail
                    )
                    .from(diary)
                    .where(diary.diaryId.eq(diaryId), diary.diaryStatus.eq(diaryStatus))
                    .fetch();

            if (content.get(0) != null) return Arrays.asList(content.get(0).split(", "));
            else return new ArrayList<String>();

        }

    @Override
    public List<UserSearchResponse> findUserByKeyword(String keyword) {

        List<UserSearchResponse> content = queryFactory
                .select(new QUserSearchResponse(
                        user.userId,
                        user.userNickName
                ))
                .from(user)
                .where(user.userNickName.eq(keyword), user.userState.eq("active"))
                .fetch();

        return content;


    }

    @Override
    public String findDiaryImgByDiaryId(Long diaryId) {

        List<String> content = queryFactory
                .select(
                        myFile.fileKey
                )
                .from(myFile)
                .leftJoin(diary)
                .on(myFile.diary.diaryId.eq(diary.diaryId))
                .where(diary.diaryId.eq(diaryId))
                .limit(1)
                .fetch();

        if (content.get(0) != null) return content.get(0);
        else return "";


    }
}
