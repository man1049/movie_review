package com.naver.man10_49.movie_web.service;

import com.naver.man10_49.movie_web.dto.UserDTO;
import com.naver.man10_49.movie_web.entity.UsersRequired;
import com.naver.man10_49.movie_web.entity.UsersRole;

import javax.servlet.http.HttpServletRequest;

public interface UserLoginPageService {
    default UsersRequired dtoToEntity(UserDTO dto){
        UsersRequired entity =
                UsersRequired.builder()
                        .user_email(dto.getUser_email()).user_password(dto.getUser_password())
                        .user_nickname(dto.getUser_nickname()).user_gender(dto.isUser_gender())
                        .user_birthday(dto.getUser_birthday()).user_answer(dto.getUser_answer())
                        .user_question(dto.getUser_question()).user_change_password_date(dto.getUser_change_password_date())
                        .user_join_date(dto.getUser_join_date()).user_email_check(dto.isUser_email_check()).user_banned(false)
                        .build();
        entity.addUserRole(UsersRole.USER);
        return entity;
    }

    default UserDTO entityToDTO(UsersRequired usersRequired){
        UserDTO dto = UserDTO.builder()
                .user_email(usersRequired.getUser_email()).user_password(usersRequired.getUser_password())
                .user_nickname(usersRequired.getUser_nickname()).user_gender(usersRequired.isUser_gender())
                .user_birthday(usersRequired.getUser_birthday()).user_answer(usersRequired.getUser_answer())
                .user_question(usersRequired.getUser_question()).user_change_password_date(usersRequired.getUser_change_password_date())
                .user_join_date(usersRequired.getUser_join_date()).user_email_check(usersRequired.isUser_email_check())
                .build();
        return dto;
    }
    
    //회원가입
    public void userJoin(HttpServletRequest httpServletRequest);

    //이메일 체크
    public boolean emailCheck(HttpServletRequest httpServletRequest);

    //닉네임 체크
    public boolean nicknameCheck(HttpServletRequest httpServletRequest);
    
    //이메일인증
    public boolean authentication(String email);

    //비밀번호 변경메일 보내기
    public boolean findPW(HttpServletRequest httpServletRequest);
    
    //비밀번호 변경하기 전 유효성검사
    public boolean pwChangePage(HttpServletRequest httpServletRequest);
    
    //비밀번호 변경
    public boolean pwChange(HttpServletRequest httpServletRequest);
    
    

}
