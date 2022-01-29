package kr.co.project.project_tj_sb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
public class PageController {
    
    @GetMapping("/")
    public String get(){
        return "redirect:/logingo";
    }

    @GetMapping("/logingo")
    public void login(@RequestParam(value = "error", required = false)String error, @RequestParam(value = "exception",required = false)String exception, Model model){
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
    }

    @GetMapping("/main")
    public void main(){}

}
