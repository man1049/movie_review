package kr.co.project.project_tj_sb.service;

import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.Review;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReviewService {
    public void reviewWrite(HttpServletRequest request, String code, MultipartFile thumbnail, UsersAuthDTO usersAuthDTO) throws IOException;
    public void reviewModify(HttpServletRequest request);
    public boolean reviewDelete(HttpServletRequest request);

    public List<Map<String,Object>> reviewListPage();
    public JSONArray reviewListPage(int page);
    public long getPage();
    public Map<String,Object> getReview(int id) throws IOException;

    public void reviewCommentWrite(int id, String comment, UsersAuthDTO usersAuthDTO);
    public JSONArray reviewCommentPage(HttpServletRequest request, UsersAuthDTO usersAuthDTO);
    public void reviewCommentDelete(HttpServletRequest request);
    public JSONObject reviewUp(HttpServletRequest request, UsersAuthDTO usersAuthDTO);
    public JSONObject reviewDown(HttpServletRequest request, UsersAuthDTO usersAuthDTO);


}
