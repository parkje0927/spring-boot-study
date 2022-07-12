package org.zerock.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.club.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    //Spring Data JPA 는 인터페이스 선언만으로도 자동으로 스프링의 빈으로 등록된다.
}
