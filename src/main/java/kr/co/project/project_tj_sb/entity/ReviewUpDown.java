package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "review_updown")
public class ReviewUpDown {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "updown_num")
    private int id;

    @Column(name = "review_num")
    private int review_num;

    @Column(name = "user_nickname")
    private String nickname;



}
