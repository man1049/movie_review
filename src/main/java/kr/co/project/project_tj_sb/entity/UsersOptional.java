package kr.co.project.project_tj_sb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "required")
@Table(name = "users_optional")
public class UsersOptional extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_optional_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_email")
    private UsersRequired required;

    @Column(name = "user_photo", length = 100)
    private String user_photo;
    @Column(name = "user_area1", length = 30)
    private String user_area1;
    @Column(name = "user_area2", length = 30)
    private String user_area2;
    @Column(name = "user_area3", length = 30)
    private String user_area3;
    @Column(name = "user_like1", length = 30)
    private String user_like1;
    @Column(name = "user_like2", length = 30)
    private String user_like2;
    @Column(name = "user_like3", length = 30)
    private String user_like3;
    
    //디폴트 값 설정
    @PrePersist
    public void defaultColumn(){
        this.user_photo = this.user_photo == null ? "default.png" : this.user_photo;
    }


}
