package kr.co.project.project_tj_sb.service;

import kr.co.project.project_tj_sb.entity.MovieComment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface MovieDetaileService {
    public void commentWrite(HttpServletRequest request, Principal principal);

    public JSONObject getCommentsInfo(HttpServletRequest request);

    public boolean isAlreadyCommentWrite(HttpServletRequest request,Principal principal);

    public JSONArray getCommentsPage(HttpServletRequest request);
}
