package kr.co.project.project_tj_sb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@Log4j2
public class PageController {
    
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
    public void main(){}

}
