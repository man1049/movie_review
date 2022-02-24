package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "review")
public class UsersReview {
    @Id
    @Column(name = "review_movie")
    private String review_movie;

    @Column(name = "user_nickname", nullable = false, length = 14)
    private String user_nickname;

    @Column(name = "user_gender", nullable = false)
    private boolean user_gender;

    @Column(name = "review_title", nullable = false, length = 28)
    private String review_title;

    @Column(name = "review_content", length = 150)
    private String review_content;

    @Column(name = "review_star", nullable = false)
    private int review_star;

    @Column(name = "review_date")
    private LocalDate review_date;

    @PrePersist
    public void defaultData(){
        LocalDate localDate = LocalDate.now();
        this.review_date = this.review_date == null ? localDate : this.review_date;
    }
}
