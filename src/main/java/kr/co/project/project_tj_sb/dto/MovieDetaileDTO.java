package kr.co.project.project_tj_sb.dto;

import kr.co.project.project_tj_sb.entity.UsersRequired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MovieDetaileDTO {

    private String movie_code;
    private UsersRequired required;
    private boolean user_gender;
    private String movie_comment;
    private double movie_star;
    private LocalDate movie_comment_date;
}
