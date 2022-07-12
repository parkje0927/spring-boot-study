package org.zerock.club.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.club.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @DisplayName("MemoRepository 인터페이스 타입의 실제 객체가 어떤 것인지 확인")
    @Test
    void testClass() {
        //결과 : jdk.proxy3.$Proxy133 => 동적 프록시 방식으로 만들어진다.
        System.out.println("memoRepository.getClass().getName() = " + memoRepository.getClass().getName());
    }

    @DisplayName("저장")
    @Test
    void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample " + i)
                    .build();
            memoRepository.save(memo);
        });
    }

    @DisplayName("조회 - findById")
    @Test
    void findByIdTest() {
        //데이터베이스를 먼저 이용한다.
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno); //여기서 SQL 이 동작
        System.out.println("====================");

        if (result.isPresent()) {
            System.out.println("result.get() = " + result.get());
        }
    }

    @DisplayName("조회 - getOne => Deprecated")
    @Transactional
    @Test
    void getOneTest() {
        //실제 객체가 필요한 순간까지 SQL 을 실행하지 않는다.
        Memo result = memoRepository.getOne(100L);
        System.out.println("====================");
        System.out.println("result = " + result); //여기서 SQL 이 동작
    }

    @DisplayName("수정")
    @Test
    void updateTest() {
        //select 호출한 뒤 이후 update 진행
        //메모리상에 보관하려고 하기 때문에 특정한 엔티티 객체가 존재하는지 확인하는 select 가 먼저 실행되고,
        //해당 @Id 를 가진 엔티티 객체가 있다면 update, 그렇지 않다면 insert 를 실행하게 된다.
        Memo memo = Memo.builder()
                .mno(100L)
                .memoText("Update Test")
                .build();
        memoRepository.save(memo);

        Optional<Memo> result = memoRepository.findById(100L);
        if (result.isPresent()) {
            System.out.println("result.get() = " + result.get());
        }
    }

    @DisplayName("삭제")
    @Transactional
    @Test
    void deleteByIdTest() {
        //select 이후 -> delete
        //해당 데이터가 존재하지 않으면 예외 발생
        memoRepository.deleteById(100L);
    }
}