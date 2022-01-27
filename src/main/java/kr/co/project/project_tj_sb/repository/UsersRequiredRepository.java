package kr.co.project.project_tj_sb.repository;

import kr.co.project.project_tj_sb.entity.UsersOptional;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRequiredRepository extends JpaRepository<UsersRequired, String> {

    //기본키인 이메일을 이용해서 데이터 불러오기
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select ur from UsersRequired ur where ur.user_email = :email")
    UsersRequired findByEmail(String email);

}