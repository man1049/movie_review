package com.naver.man10_49.movie_web.repository;

import com.naver.man10_49.movie_web.entity.Review;
import com.naver.man10_49.movie_web.entity.UsersRequired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select rv from Review rv order by rv.id desc ")
    List<Review> findAllOrderById(@PageableDefault Pageable pageable);

    @Query("select rv from Review rv where rv.usersRequired.user_nickname like %:nickname% order by rv.id desc ")
    List<Review> findAllByUsersRequired(@PageableDefault Pageable pageable, String nickname);

    @Query("select rv from Review rv where rv.movie_name like %:keyword% order by rv.id desc ")
    List<Review> findAllByMovie_name(@PageableDefault Pageable pageable, String keyword);

    @Query("select rv from Review rv where rv.review_title like %:keyword% order by rv.id desc ")
    List<Review> findAllByReview_title(@PageableDefault Pageable pageable, String keyword);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Review rv set rv.review_up = rv.review_up+1 where rv.id = :review_num")
    void updateReviewUp(int review_num);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Review rv set rv.review_down = rv.review_down+1 where rv.id = :review_num")
    void updateReviewDown(int review_num);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Review rv set rv.review_content = :review_content, rv.moddate = :moddate where rv.id = :review_num")
    void updateReviewContent(int review_num, String review_content, LocalDate moddate);
}
