package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.dto.UserDTO;
import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import kr.co.project.project_tj_sb.service.MovieDetaileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


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
    @CacheEvict(allEntries = true) // 캐시를 전부 제거함
    public void login(){}

    @GetMapping("/main")
    public void main(/*Model model, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO*/){}

    @GetMapping("/movielist")
    public String korean(){
        return "movielist";
    }

    @GetMapping("/detaile/{code}")
    public String detaileReview(HttpServletRequest request,Model model, Principal principal, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO, @PathVariable(required = false) String code){
        boolean isAlreayComment = movieDetaileService.isAlreadyCommentWrite(code,principal);

        boolean admin = usersAuthDTO.getAuthorities().size() == 2;
        log.info("디테일 페이지 접근");
        log.info("어드민 : "+admin);
        log.info(usersAuthDTO.getAuthorities());
        log.info(code);
        log.info(isAlreayComment);
        model.addAttribute("role",admin);
        model.addAttribute("alreadyComment",isAlreayComment);
        model.addAttribute("code",code);
        log.info("code 모델값 : "+model.getAttribute("code"));
        return "detaile";
    }

}
