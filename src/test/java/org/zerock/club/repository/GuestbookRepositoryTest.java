package org.zerock.club.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.club.entity.Guestbook;
import org.zerock.club.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("Writer...." + i)
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    //특정 엔티티 수정 후에는 modDate 에는 최종 수정 시간이 적용된다.
    @Test
    void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");

            guestbookRepository.save(guestbook);
        }
    }

    /**
     * 1. 제목/내용/작성자 처럼 단 하나의 항목으로 검색하는 경우
     */
    @Test
    void testQuery1() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("gno"));

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(g -> {
            System.out.println("g = " + g);
        });
    }

    /**
     * 2. 제목 + 내용 or 내용 + 작성자 or 제목 + 작성자 처럼 2개의 항목으로 검색하는 경우
     */
    @Test
    void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        //gno 는 5이상 200이하 && title 은 5를 가지고 있어야 한다.
        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder builder = new BooleanBuilder();

        //title 조건
        String keyword = "5";
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        //gno 조건
        BooleanExpression exGno = qGuestbook.gno.goe(5).and(qGuestbook.gno.loe(200));

        builder.and(exTitle).and(exGno);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(g -> {
            System.out.println("g = " + g);
        });
    }

    /**
     * 3. 제목 + 내용 + 작성자 처럼 3개의 항목으로 검색하는 경우
     */
    @Test
    void testQuery3() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("gno").descending());

        //제목이나 내용 혹은 작성자에 특정 키워드가 들어가 있는 경우
        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exWriter = qGuestbook.writer.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent).or(exWriter);

        //gno 가 5 이상 50 이하인 경우
        builder.and(exAll).and(qGuestbook.gno.goe(5)).and(qGuestbook.gno.loe(50));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(g -> {
            System.out.println("g = " + g);
        });
    }
}