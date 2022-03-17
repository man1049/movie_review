package kr.co.project.project_tj_sb.repository;

import kr.co.project.project_tj_sb.entity.MovieComment;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDetailRepository extends JpaRepository<MovieComment,Integer> {
    @Query("select mc from MovieComment mc where mc.movie_code = :movie_code order by mc.id desc ")
    List<MovieComment> findByMovie_codeOrderByIdAsc(String movie_code);


    @Query("select mc from MovieComment mc where mc.movie_code = :movie_code order by mc.id desc")
    List<MovieComment> findByMovie_codeOrderByIdAsc(String movie_code, @PageableDefault Pageable pageable);

    @Query("select count(mc) from MovieComment mc where mc.usersRequired = :required and mc.movie_code = :movie_code")
    int countByUser_nickname(UsersRequired required, String movie_code);

}
