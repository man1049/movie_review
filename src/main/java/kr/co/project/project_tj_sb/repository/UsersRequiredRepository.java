package kr.co.project.project_tj_sb.repository;

import kr.co.project.project_tj_sb.entity.UsersOptional;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRequiredRepository extends JpaRepository<UsersRequired, String> {

    //기본키인 이메일을 이용해서 데이터 불러오기
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ur from UsersRequired ur where ur.user_email = :email")
    UsersRequired findByEmail(String email);

    
    //이메일 중복 체크
    @Query("select count(ur) from UsersRequired ur where ur.user_email = :email")
    int countByUser_email(String email);

    //닉네임 중복 체크
    @Query("select count(ur) from UsersRequired ur where ur.user_nickname = :nickname")
    int countByUser_nickname(String nickname);

    // 이메일 인증
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UsersRequired ur set ur.user_email_check = true where ur.user_email = :user_email ")
    int updateUserEmailCheck(String user_email);

    //이메일 확인 이메일을 중복해서 인증 체크
    @Query("select ur.user_email_check from UsersRequired ur where ur.user_email = :user_email")
    boolean getByUser_email_check(String user_email);

}