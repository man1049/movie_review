package com.naver.man10_49.movie_web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.beans.BeanProperty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDTO {
    private String user_email;
    private String user_password;
    private String user_nickname;
    private boolean user_gender;
    private LocalDate user_birthday;
    private String user_answer;
    private String user_question;
    private LocalDate user_change_password_date;
    private LocalDate user_join_date;
    private boolean user_email_check;
}
