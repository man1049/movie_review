package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "user_nickname")
@Table(name = "movie_comment")
public class MovieComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_comment_id")
    private int id;
/*
    @OneToOne
    @JoinColumn(name = "user_email")
    private UsersRequired required;
*/

    @ManyToOne(fetch = FetchType.LAZY)
    private UsersRequired usersRequired;

    @Column(name = "user_age", nullable = false)
    private int user_age;

    @Column(name = "movie_code")
    private String movie_code;

    @Column(name = "movie_comment", length = 150)
    private String movie_comment;

    @Column(name = "movie_star", nullable = false)
    private double movie_star;

}
