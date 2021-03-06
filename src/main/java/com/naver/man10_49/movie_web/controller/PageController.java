package com.naver.man10_49.movie_web.controller;

import com.naver.man10_49.movie_web.service.MovieDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@Controller
@Log4j2
@RequiredArgsConstructor
public class PageController {

    //private final MovieAPIParse movieAPIParse;
    private final MovieDetailService movieDetailService;
    
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
    @CacheEvict(allEntries = true) // 캐시를 전부 제거함
    public void login(){}

    @GetMapping("/main")
    public void main(/*Model model, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO*/){}

    @GetMapping("/movielist")
    public String movieList(){
        return "movielist";
    }



}
