package kr.co.project.project_tj_sb.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
public class PageController {
    
    @GetMapping("/")
    public String get(){
        log.info("페이지 첫 접속");
        return "/logingo";
    }

    @GetMapping("/logingo")
    public void login(){
        log.info("로그인페이지 접속");
    }

    @GetMapping("/main")
    public void main(){
        log.info("메인페이지 접속");
    }

    @GetMapping("/logout")
    public void logout(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.logout();
    }
}
