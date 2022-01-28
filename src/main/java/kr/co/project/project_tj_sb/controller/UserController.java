package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/join")
    public String joinUser(Model model, HttpServletRequest httpServletRequest){
        userService.userJoin(httpServletRequest);
        return "redirect:/";
    }

    @PostMapping("/join/emailcheck")
    @ResponseBody
    public boolean emailCheck(Model model, HttpServletRequest httpServletRequest){
        boolean emailCheck = false;
        emailCheck = userService.emailCheck(httpServletRequest);
        return emailCheck;
    }

    @PostMapping("/join/nicknamecheck")
    @ResponseBody
    public boolean nicknameCheck(Model model, HttpServletRequest httpServletRequest){
        boolean nicknameCheck = false;
        nicknameCheck = userService.nicknameCheck(httpServletRequest);
        return nicknameCheck;
    }
}
