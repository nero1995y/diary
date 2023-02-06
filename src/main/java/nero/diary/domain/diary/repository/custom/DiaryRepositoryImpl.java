package nero.diary.domain.diary.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import nero.diary.domain.diary.dto.DiaryResponseDto;
import nero.diary.domain.diary.dto.QDiaryResponseDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static nero.diary.domain.diary.entity.QDiary.diary;
import static nero.diary.domain.user.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;


public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DiaryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page <DiaryResponseDto> search(DiarySearchCondition condition, Pageable pageable) {


        List<DiaryResponseDto> queryResult = queryFactory
                .select(new QDiaryResponseDto(
                        diary.id.as("diaryId"),
                        diary.name,
                        diary.content,
                        user.email.as("userEmail")))
                .from(diary)
                .leftJoin(diary.user, user)
                .where(diaryNameEq(condition.getName()),
                        contentEq(condition.getContext()),
                        userNameEq(condition.getUsername())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diary.count())
                .from(diary)
                .leftJoin(diary.user, user)
                .where(diaryNameEq(condition.getName()),
                        contentEq(condition.getContext()),
                        userNameEq(condition.getUsername())
                );


        return  PageableExecutionUtils.getPage(queryResult, pageable, countQuery::fetchOne);
    }

    private BooleanExpression diaryNameEq(String name) {
        return hasText(name) ? diary.name.eq(name) : null;
    }

    private BooleanExpression contentEq(String content) {
        return hasText(content) ? diary.content.eq(content) : null;
    }

    private BooleanExpression userNameEq(String username) {
        return hasText(username) ? user.email.eq(username) : null;
    }
}
