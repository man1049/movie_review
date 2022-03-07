package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.entity.MovieComment;
import kr.co.project.project_tj_sb.service.MovieDetaileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DetaileController {
    private final MovieDetaileService movieDetaileService;

    @PostMapping("/detaile/write")
    public String detaileCommentWrite(HttpServletRequest request, HttpServletResponse response, Principal principal){
        movieDetaileService.commentWrite(request, principal);
        return "redirect:/detaile?code="+request.getParameter("code");
    }

    @GetMapping("/detaile/comments/info")
    @ResponseBody
    public JSONObject detaileCommentsInfo(HttpServletRequest request){
        return movieDetaileService.getCommentsInfo(request);
    }

    @GetMapping("/detaile/comments/page")
    @ResponseBody
    public JSONArray detaileCommentsPage(HttpServletRequest request){
        return movieDetaileService.getCommentsPage(request);
    }
}
