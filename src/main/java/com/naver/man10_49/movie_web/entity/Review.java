package com.naver.man10_49.movie_web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "usersRequired")
@Table(name = "review")
public class Review{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_num")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_email")
    private UsersRequired usersRequired;

    @Column(name = "movie_name")
    private String movie_name;

    @Column(name = "movie_code")
    private String movie_code;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "regdate")
    private LocalDate regdate;

    @Column(name = "moddate")
    private LocalDate moddate;

    @Column(name = "review_star", length = 1)
    private int review_star;

    @Column(name = "review_title", nullable = false, length = 64)
    private String review_title;

    @Column(name = "review_content", nullable = false, length = 500000)
    private String review_content;

    @Column(name = "review_up")
    private int review_up;

    @Column(name = "review_down")
    private int review_down;

    @PrePersist
    public void prePersist(){
        LocalDate localDate = LocalDate.now();
        this.regdate = this.regdate == null ? localDate : this.regdate;
        this.review_up = 0;
        this.review_down = 0;
    }
}
