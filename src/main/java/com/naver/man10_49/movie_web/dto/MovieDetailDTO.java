package com.naver.man10_49.movie_web.dto;

import com.naver.man10_49.movie_web.entity.UsersRequired;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MovieDetailDTO {

    private String movie_code;
    private UsersRequired required;
    private boolean user_gender;
    private String movie_comment;
    private double movie_star;
    private LocalDate movie_comment_date;
}
