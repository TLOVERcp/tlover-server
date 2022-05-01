package com.example.tlover.domain.reply.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.reply.dto.QReplyGetResponse;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import com.example.tlover.domain.scrap.dto.QDiaryInquiryByScrapRankingResponse;
import com.example.tlover.domain.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.tlover.domain.reply.entity.QReply.reply;
import static com.example.tlover.domain.scrap.entity.QScrap.scrap;

public class ReplyRepositoryImpl implements ReplyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReplyRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<ReplyGetResponse> findByDiaryCustom(Diary diary, Pageable pageable) {

        OrderSpecifier orderBy = reply.replyTime.asc();

        if (!pageable.getSort().isEmpty()) {
            Sort.Direction direction = pageable.getSort().getOrderFor("replyTime").getDirection();

            if (direction == Sort.Direction.DESC) orderBy = reply.replyTime.desc();
        }

        List<ReplyGetResponse> content = queryFactory
                .select(new QReplyGetResponse(
                        reply.replyId,
                        reply.diary.diaryId,
                        reply.replyContext,
                        reply.replyState,
                        reply.user.userNickName,
                        reply.replyTime
                ))
                .orderBy(orderBy)
                .where(diaryEq(diary), reply.replyState.eq("ACTIVE"))
                .from(reply)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ReplyGetResponse> countQuery = queryFactory
                .select(new QReplyGetResponse(
                        reply.replyId,
                        reply.diary.diaryId,
                        reply.replyContext,
                        reply.replyState,
                        reply.user.userNickName,
                        reply.replyTime
                ))
                .orderBy(orderBy)
                .where(diaryEq(diary), reply.replyState.eq("ACTIVE"))
                .from(reply);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }

    private BooleanExpression diaryEq(Diary diary) {return diary != null ? reply.diary.eq(diary) : null;}
}
