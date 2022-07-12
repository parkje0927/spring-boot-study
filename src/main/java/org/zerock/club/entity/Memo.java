package org.zerock.club.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    /**
     * @Entity : JPA 로 관리되는 엔티티 객체라는 것을 의미한다.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) => auto increment
     * @Transient : 테이블에 컬럼으로 생성되지 않는 필드의 경우 붙이는 어노테이션
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

}
