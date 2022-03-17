package kr.co.project.project_tj_sb.repository;

import kr.co.project.project_tj_sb.entity.ReviewUpDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewUpDownRepository extends JpaRepository<ReviewUpDown, Integer> {

    @Query("select count(ud) from ReviewUpDown ud where ud.nickname = :nickname and ud.review_num = :review_num")
    int countReviewUpDownByNicknameAndReview_num(String nickname, int review_num);

    @Query("select ud from ReviewUpDown ud where ud.review_num = :review_num")
    List<ReviewUpDown> findByReview_num(int review_num);

}
