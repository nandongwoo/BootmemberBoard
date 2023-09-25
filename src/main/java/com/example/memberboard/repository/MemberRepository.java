package com.example.memberboard.repository;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.Optional;

// interface : 실행문구가 없는 추상 메서드 정의
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 추상메서드
    // 1. select * from member_table where member_email = ?
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


    // 2. select * from member_table where member_email = ? and member_password = ?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}
