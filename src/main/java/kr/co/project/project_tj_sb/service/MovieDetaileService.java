package kr.co.project.project_tj_sb.service;

import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.MovieComment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface MovieDetaileService {
    public void commentWrite(HttpServletRequest request, Principal principal);

    public JSONObject getCommentsInfo(HttpServletRequest request, UsersAuthDTO usersAuthDTO);
    public JSONArray getCommentsPage(HttpServletRequest request, UsersAuthDTO usersAuthDTO);


    public boolean isPosterImgUpload(String code, MultipartFile img);
    public boolean isActorImgUpload(String code, MultipartFile img);
    public boolean isAlreadyCommentWrite(String code,Principal principal);

    public void deleteComment(HttpServletRequest request);
}
