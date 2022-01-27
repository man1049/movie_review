package kr.co.project.project_tj_sb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    /////////////////////////////////
    private LocalDate user_join_date;
    private LocalDate user_change_password_date;
    private boolean user_email_check;

    // 디폴트 값 설정
    @PrePersist
    public void defaultData(){
        LocalDate localDate = LocalDate.now();
        this.user_join_date = this.user_join_date == null ? localDate : this.user_join_date;
        this.user_change_password_date = this.user_change_password_date == null ? localDate : this.user_change_password_date;
    }



    /*
    public void usersRequiredSet(String user_email, String user_password
            ,String user_nickname,boolean user_gender,LocalDate user_birthday
            ,String user_question,String user_answer){
        LocalDate localDate = LocalDate.now();
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_nickname = user_nickname;
        this.user_gender = user_gender;
        this.user_birthday = user_birthday;
        this.user_question = user_question;
        this.user_answer = user_answer;
        this.user_join_date = localDate;
        this.user_change_password_date = localDate;
        this.user_email_check = false;
    }
    */

/*
    @OneToMany(mappedBy = "required")
    private List<UsersResHistory> usersResHistory;
*/

    @OneToOne(mappedBy = "required", fetch = FetchType.LAZY)
    private UsersOptional usersOptional;



    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<UsersRole> roleSet = new HashSet<>();

    public void addUserRole(UsersRole usersRole) {
        roleSet.add(usersRole);
    }
}
