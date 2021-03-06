package com.naver.man10_49.movie_web.repository;

import com.naver.man10_49.movie_web.entity.ReviewComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment,Integer> {

    @Query("select rvc from ReviewComment rvc where rvc.review_num = :review_num")
    List<ReviewComment> findByReview_num(int review_num, @PageableDefault Pageable pageable);

    @Query("select rvc from ReviewComment rvc where rvc.review_num = :review_num")
    List<ReviewComment> findByReview_num(int review_num);



}
