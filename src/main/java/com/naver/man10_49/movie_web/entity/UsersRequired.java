package com.naver.man10_49.movie_web.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "users_required")
public class UsersRequired{
    @Id
    @Column(name = "user_email",length = 50)
    private String user_email;
    @Column(name = "user_password", nullable = false , length = 100)
    private String user_password;
    @Column(name = "user_nickname", nullable = false, unique = true, length = 14)
    private String user_nickname;
    @Column(name = "user_gender", nullable = false)
    private boolean user_gender;
    @Column(name = "user_birthday", nullable = false)
    private LocalDate user_birthday;
    @Column(name = "user_question", nullable = false)
    private String user_question;
    @Column(name = "user_answer", nullable = false)
    private String user_answer;
    @Column(name = "banned", nullable = false)
    private boolean user_banned;
    /////////////////////////////////
    private LocalDate user_join_date;
    private LocalDate user_change_password_date;
    private boolean user_email_check;

    // 디폴트 값 설정
    //@PrePersist는 가장먼저 콜백시키는 어노테이션이다.
    //지금 사용의 용도는 user_join_date는 현재시간을 넣어야하므로
    //null일시 현재시간을 기입해준다.
    //직접 넣어줬다면 직접넣은 날짜를 기입해준다.
    @PrePersist
    public void defaultData(){
        LocalDate localDate = LocalDate.now();
        this.user_join_date = this.user_join_date == null ? localDate : this.user_join_date;
        this.user_change_password_date = this.user_change_password_date == null ? localDate : this.user_change_password_date;
    }

/*
    @OneToMany(mappedBy = "users_review")
    private List<> usersResHistory;
*/
/*
    @OneToOne(mappedBy = "required", fetch = FetchType.LAZY)
    private MovieComment movieComment;
*/
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<UsersRole> roleSet = new HashSet<>();

    public void addUserRole(UsersRole usersRole) {
        roleSet.add(usersRole);
    }

}
