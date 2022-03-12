package kr.co.project.project_tj_sb.controller;

import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.MovieComment;
import kr.co.project.project_tj_sb.service.MovieDetaileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DetaileController {
    private final MovieDetaileService movieDetaileService;

    @PostMapping("/detaile/comment/write")
    public String detaileCommentWrite(HttpServletRequest request, HttpServletResponse response, Principal principal){
        movieDetaileService.commentWrite(request, principal);
        return "redirect:/detaile/"+request.getParameter("code");
    }

    @GetMapping("/detaile/comments/info")
    @ResponseBody
    public JSONObject detaileCommentsInfo(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return movieDetaileService.getCommentsInfo(request, usersAuthDTO);
    }

    @GetMapping("/detaile/comments/page")
    @ResponseBody
    public JSONArray detaileCommentsPage(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return movieDetaileService.getCommentsPage(request, usersAuthDTO);
    }


    // 댓글 삭제
    @GetMapping("/detaile/comment/delete")
    @ResponseBody
    public void deleteComment(HttpServletRequest request){
        movieDetaileService.deleteComment(request);
    }
    
    // 이미지파일 업로드 (포스터-어드민전용)
    @PostMapping("/detaile/modifyposterimg")
    public String modifyImg(Model model, @RequestParam("code") String code, @RequestParam("image") MultipartFile img)throws Exception{
        boolean flag = movieDetaileService.isPosterImgUpload(code,img);
        // log.info(flag);
        return "redirect:/detaile/"+code;
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
                // log.info("포스터 정상 출력");
            }catch (IOException e){
                try {
                    posterStream = new FileInputStream("/movieposter/default.png");
                    imageByteArray = posterStream.readAllBytes();
                    posterStream.close();
                    // log.info("default.png 출력");
                }catch (IOException e2){
                    // log.info("default.png 파일이 없습니다.");
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
                // log.info("default.png 파일이 없습니다.");
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
                // log.info("default.png 파일이 없습니다.");
            }
        }
        // log.info("이미지 출력");
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }
}
