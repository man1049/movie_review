package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "required")
@Table(name = "movie_comment")
public class MovieComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_comment_id")
    private int id;
/*
    @OneToOne
    @JoinColumn(name = "user_email")
    private UsersRequired required;
*/
    @Column(name = "user_nickname")
    private String user_nickname;

    @Column(name = "user_gender", nullable = false)
    private boolean user_gender;

    @Column(name = "user_age", nullable = false)
    private int user_age;

    @Column(name = "movie_code")
    private String movie_code;

    @Column(name = "movie_comment", length = 150)
    private String movie_comment;

    @Column(name = "movie_star", nullable = false)
    private double movie_star;

    @Column(name = "movie_comment_date")
    private LocalDate movie_comment_date;

    @PrePersist
    public void defaultData(){
        LocalDate localDate = LocalDate.now();
        this.movie_comment_date = this.movie_comment_date == null ? localDate : this.movie_comment_date;
    }
}
