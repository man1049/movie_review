package com.naver.man10_49.movie_web.controller;

import com.naver.man10_49.movie_web.dto.UsersAuthDTO;
import com.naver.man10_49.movie_web.service.MovieDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DetailController {
    private final MovieDetailService movieDetailService;

    @GetMapping("/detail/{code}")
    public String detailReview(HttpServletRequest request, Model model, Principal principal, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO, @PathVariable(required = false) String code){
        boolean isAlreayComment = movieDetailService.isAlreadyCommentWrite(code,principal);
        boolean admin = usersAuthDTO.getAuthorities().size() == 2;

        model.addAttribute("role",admin);
        model.addAttribute("alreadyComment",isAlreayComment);
        model.addAttribute("code",code);
        return "detail";
    }

    @PostMapping("/detail/comment/write")
    public String detailCommentWrite(HttpServletRequest request, HttpServletResponse response, Principal principal){
        movieDetailService.commentWrite(request, principal);
        return "redirect:/detail/"+request.getParameter("code");
    }

    @GetMapping("/detail/comments/info")
    @ResponseBody
    public JSONObject detailCommentsInfo(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return movieDetailService.getCommentsInfo(request, usersAuthDTO);
    }

    @GetMapping("/detail/comments/page")
    @ResponseBody
    public JSONArray detailCommentsPage(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return movieDetailService.getCommentsPage(request, usersAuthDTO);
    }


    // 댓글 삭제
    @GetMapping("/detail/comment/delete")
    @ResponseBody
    public void deleteComment(HttpServletRequest request){
        movieDetailService.deleteComment(request);
    }
    
    // 이미지파일 업로드 (포스터-어드민전용)
    @PostMapping("/detail/modifyposterimg")
    public String modifyImg(Model model, @RequestParam("code") String code, @RequestParam("image") MultipartFile img)throws Exception{
        boolean flag = movieDetailService.isPosterImgUpload(code,img);
        // log.info(flag);
        return "redirect:/detail/"+code;
    }
    
    // 이미지파일 불러오기
    @GetMapping("/poster/{code}")
    public ResponseEntity<byte[]> poster(@PathVariable("code") String code){
        InputStream posterStream;
        byte[] imageByteArray = new byte[0];
            try {
                posterStream = new FileInputStream("/movieposter/" + code + ".png");
                imageByteArray = posterStream.readAllBytes();
                posterStream.close();
                //log.info("포스터 정상 출력");
            }catch (IOException e){
                try {
                    posterStream = new FileInputStream("/movieposter/default.png");
                    imageByteArray = posterStream.readAllBytes();
                    posterStream.close();
                    // log.info("default.png 출력");
                }catch (IOException e2){
                    log.info("default.png 파일이 없습니다.");
                }
            }
        // log.info("이미지 출력");
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

    @GetMapping("/actor/{code}")
    public ResponseEntity<byte[]> actor(@PathVariable("code") String code){
        InputStream posterStream;
        byte[] imageByteArray = new byte[0];
        // log.info("배우 사진");
        // log.info(code);
        if(code == null){
            try {
                posterStream = new FileInputStream("/movieactor/default.png");
                imageByteArray = posterStream.readAllBytes();
                posterStream.close();
                // log.info("default.png 출력");
            }catch (IOException e2){
                log.info("default.png 파일이 없습니다.");
            }
        }
        try {
            posterStream = new FileInputStream("/movieactor/" + code + ".png");
            imageByteArray = posterStream.readAllBytes();
            posterStream.close();
            // log.info("포스터 정상 출력");
        }catch (IOException e){
            try {
                posterStream = new FileInputStream("/movieactor/default.png");
                imageByteArray = posterStream.readAllBytes();
                posterStream.close();
                // log.info("default.png 출력");
            }catch (IOException e2){
                log.info("default.png 파일이 없습니다.");
            }
        }
        // log.info("이미지 출력");
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }
}
