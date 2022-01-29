package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.service.UserJoinService;
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
public class UserJoinController {

    private final UserJoinService userJoinService;


    @PostMapping("/join")
    public String joinUser(Model model, HttpServletRequest httpServletRequest){
        userJoinService.userJoin(httpServletRequest);
        return "redirect:/";
    }

    @PostMapping("/join/emailcheck")
    @ResponseBody
    public boolean emailCheck(Model model, HttpServletRequest httpServletRequest){
        return userJoinService.emailCheck(httpServletRequest);
    }

    @PostMapping("/join/nicknamecheck")
    @ResponseBody
    public boolean nicknameCheck(Model model, HttpServletRequest httpServletRequest){
        return userJoinService.nicknameCheck(httpServletRequest);
    }

    @PostMapping("/join/authentication")
    public String setEmailCheck(HttpServletRequest request){
        log.info("========================================");
        log.info(request.getParameter("user_email"));
        boolean check = userJoinService.authentication(request.getParameter("user_email"));
        return "redirect:/logingo?authentication="+check;
    }
}
