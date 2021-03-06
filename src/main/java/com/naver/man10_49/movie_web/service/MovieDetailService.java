package com.naver.man10_49.movie_web.service;

import com.naver.man10_49.movie_web.dto.UsersAuthDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface MovieDetailService {
    public void commentWrite(HttpServletRequest request, Principal principal);

    public JSONObject getCommentsInfo(HttpServletRequest request, UsersAuthDTO usersAuthDTO);
    public JSONArray getCommentsPage(HttpServletRequest request, UsersAuthDTO usersAuthDTO);


    public boolean isPosterImgUpload(String code, MultipartFile img);
    public boolean isActorImgUpload(String code, MultipartFile img);
    public boolean isAlreadyCommentWrite(String code,Principal principal);

    public void deleteComment(HttpServletRequest request);
}
