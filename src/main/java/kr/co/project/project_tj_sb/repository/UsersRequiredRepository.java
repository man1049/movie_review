package kr.co.project.project_tj_sb.repository;

//import kr.co.project.project_tj_sb.entity.UsersOptional;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    //이메일 확인 - 이미 이메일 인증이 완료되었을 시 다시 인증버튼을 누르는 것을 방지
    @Query("select ur.user_email_check from UsersRequired ur where ur.user_email = :user_email")
    boolean getByUser_email_check(String user_email);

    //비밀번호 찾기를 위한 이메일확인
    @Query("select count(ur) from UsersRequired ur where ur.user_email = :user_email and ur.user_question = :user_question and ur.user_answer = :user_answer")
    int findByUser_emailAndUser_questionAndUser_answer(String user_email,String user_question,String user_answer);

    //확인메일 및 비밀번호 변경메일을 보내기위한 닉네임 찾기
    @Query("select ur.user_nickname from UsersRequired ur where ur.user_email = :user_email")
    String findByUser_nickname(String user_email);
    
    //비밀번호 변경
    @Transactional
    @Modifying
    @Query("update UsersRequired ur set ur.user_password = :user_password, ur.user_change_password_date = :user_change_password_date where ur.user_email = :user_email")
    int updateUser_password(String user_email, String user_password, LocalDate user_change_password_date);
}