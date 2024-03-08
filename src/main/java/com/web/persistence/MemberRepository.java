package com.web.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입되는 사용자인지 판단하기 위한 메서드
    Optional<Member> findByEmail(String email);
	
    // 주민번호로 조회
	@Query("SELECT m FROM Member m WHERE m.socialSecuNum like %:searchKeyword")
	List<Member> socialSecuNumCheck(@Param("searchKeyword") String searchKeyword);
 
	// 전화번호로 조회
	@Query("SELECT m FROM Member m WHERE m.phoneNumber like %:searchKeyword")
	List<Member> phoneNumberCheck(@Param("searchKeyword") String searchKeyword);

	// 랭킹 상위 3명
	List<Member> findTop3ByOrderByTotalPointDesc();

	// 누적 포인트 순으로 멤버 조회
	@Query(value = "SELECT RANK() OVER(ORDER BY MEMBER_TOTAL_POINT DESC) rank , m.* FROM MEMBER_TB m", nativeQuery = true)
	List<Member> rankCheck();
	
	// 닉네임으로 조회
	Member findByNickName(String searchKeyword);

	// 이름, 주민번호, 전화번호로 아이디 찾기
	Member findByNameAndSocialSecuNumAndPhoneNumber(String name, String socialSecuNum, String phoneNumber); 

	Member findByIdAndNameAndSocialSecuNumAndPhoneNumber(String id, String name, String socialSecuNum, String phoneNumber);
	
	@Query("SELECT m FROM Member m ORDER BY m.id DESC")
    List<Member> findAllDesc();
}
