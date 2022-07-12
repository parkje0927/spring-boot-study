package org.zerock.club.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
}
