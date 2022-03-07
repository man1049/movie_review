package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.dto.UserDTO;
import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.MovieComment;
import kr.co.project.project_tj_sb.service.MovieAPIParse;
import kr.co.project.project_tj_sb.service.MovieDetaileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Controller
@Log4j2
@RequiredArgsConstructor
public class PageController {

    //private final MovieAPIParse movieAPIParse;
    private final MovieDetaileService movieDetaileService;
    
    @GetMapping("/")
    public String get(RedirectAttributes attributes,HttpServletRequest request){
        String loginExeption = request.getParameter("exception");
        String auth = request.getParameter("authentication");
        String join = request.getParameter("join");
        log.info(request.getParameter("join"));
        String findpw = request.getParameter("findpw");
        String pwchange = request.getParameter("pwchange");
        if(auth != null){
            attributes.addFlashAttribute("auth",auth);
        }
        if(join != null){
            attributes.addFlashAttribute("join",join);
            log.info(request.getParameter("join"));
        }
        if(findpw != null){
            attributes.addFlashAttribute("findpw",findpw);
        }
        if(pwchange != null){
            attributes.addFlashAttribute("pwchange",pwchange);
        }
        if(loginExeption != null){
            attributes.addFlashAttribute("loginexception",loginExeption);
        }
        return "redirect:/logingo";
    }

    @GetMapping("/logingo")
    public void login(){}

    @GetMapping("/main")
    public void main(/*HttpServletRequest request, ModelMap model*/){
        //model.addAttribute("top10",movieAPIParse.movieTop10());
    }

    @GetMapping("/korean")
    public String korean(){
        return "korean";
    }

    @GetMapping("/detaile")
    public void detaileReview(HttpServletRequest request,Model model, Principal principal){
        boolean isAlreayComment = movieDetaileService.isAlreadyCommentWrite(request,principal);
        model.addAttribute("alreadyComment",isAlreayComment);
    }

}
