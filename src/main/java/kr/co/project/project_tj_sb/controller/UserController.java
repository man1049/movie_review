package kr.co.project.project_tj_sb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class UserController {

    @PostMapping("user/join")
    public String joinUser(Model model){

        return "redirect:/";
    }
}
