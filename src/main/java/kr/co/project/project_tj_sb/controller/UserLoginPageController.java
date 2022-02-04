package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.service.UserLoginPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserLoginPageController {

    private final UserLoginPageService userLoginPageService;


    @PostMapping("/join")
    public String joinUser(Model model, HttpServletRequest httpServletRequest){
        userLoginPageService.userJoin(httpServletRequest);
        return "redirect:/logingo?join=true";
    }

    @PostMapping("/join/emailcheck")
    @ResponseBody
    public boolean emailCheck(HttpServletRequest httpServletRequest){
        return userLoginPageService.emailCheck(httpServletRequest);
    }

    @PostMapping("/join/nicknamecheck")
    @ResponseBody
    public boolean nicknameCheck(HttpServletRequest httpServletRequest){
        return userLoginPageService.nicknameCheck(httpServletRequest);
    }

    @PostMapping("/join/authentication")
    public String setEmailCheck(HttpServletRequest request){
        boolean check = userLoginPageService.authentication(request.getParameter("user_email"));
        return "redirect:/?authentication="+check;
    }

    @PostMapping("/findpw")
    public String findPW(HttpServletRequest httpServletRequest){
        boolean checkEmail = userLoginPageService.findPW(httpServletRequest);
        return "redirect:/?findpw="+checkEmail;
    }

    @GetMapping("/pwchange")
    public void getPwChangePage(){}

    @PostMapping("/pwchange")
    @ResponseBody
    public boolean pwChange(HttpServletRequest httpServletRequest){
        return userLoginPageService.pwChangePage(httpServletRequest);
    }
    @PostMapping("/pwchange/change")
    public String setPwChange(HttpServletRequest httpServletRequest){
        boolean changeCheck = userLoginPageService.pwChange(httpServletRequest);
        return "redirect:/?pwchange="+changeCheck;
    }
}