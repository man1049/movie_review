package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "usersRequired")
@Table(name = "review_comment")
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rc_num")
    private int id;

    @Column(name = "review_num")
    private int review_num;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_email")
    private UsersRequired usersRequired;

    @Column(name = "rc_comment")
    private String comment;

    @Column(name = "rc_regdate")
    private LocalDate regdate;

    @PrePersist
    public void defaultValue(){
        this.regdate = this.regdate == null ? LocalDate.now() : this.regdate;
    }
}
