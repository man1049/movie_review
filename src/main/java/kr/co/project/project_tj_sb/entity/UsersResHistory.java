package kr.co.project.project_tj_sb.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "user_res_history")
public class UsersResHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_res_history_id")
    private int id;

    @Column(nullable = false)
    private int res_num;
    @Column(nullable = false)
    private int menu_num;
    @Column(nullable = false)
    private int visit_count;

    /*
    @ManyToOne
    @JoinColumn(name = "user_email")
    private UsersRequired required;
*/


}
