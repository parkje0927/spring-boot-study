package org.zerock.club.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.club.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    //Spring Data JPA 는 인터페이스 선언만으로도 자동으로 스프링의 빈으로 등록된다.

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    @Query(value = "select * from memo where mno > 0", nativeQuery = true)
    List<Object[]> findNativeResult();
    
}
