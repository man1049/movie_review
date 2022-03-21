package com.naver.man10_49.movie_web.controller;

import com.naver.man10_49.movie_web.dto.UsersAuthDTO;
import com.naver.man10_49.movie_web.service.ReviewService;
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
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/review/{code}")
    public String getReview(Model model, @PathVariable int code, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO) throws IOException {
        Map<String,Object> data = reviewService.getReview(code);
        boolean isAuth = usersAuthDTO.getAuthorities().size() > 1;

        data.put("auth",isAuth);
        data.put("nickname",usersAuthDTO.getUser_nickname());
        model.addAttribute("data",data);
        return "review";
    }

    @GetMapping("/reviewlist")
    public String reviewList(Model model){
        List<Map<String,Object>> list = reviewService.reviewListPage();
        model.addAttribute("reviews",list);
        return "reviewlist";
    }

    @GetMapping("/reviewlist/page")
    @ResponseBody
    public JSONArray reviewListPage(
            @RequestParam("page")int page, @RequestParam("keyword")String keyword, @RequestParam("search")String search){
        return reviewService.reviewListSearchPage(page, keyword, search);
    }

    @GetMapping("/review/write/{code}/{moviename}")
    public String reviewWrite(Model model,@PathVariable("code") String code,@PathVariable("moviename") String movieName){

        model.addAttribute("code",code);
        model.addAttribute("moviename",movieName);
        return "reviewwrite";
    }

    @PostMapping("/review/write/{code}")
    public String reviewWriteSubmit(HttpServletRequest request, @PathVariable("code")String code,@RequestParam("thumbnail")MultipartFile thumbnail, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO) throws IOException {

        reviewService.reviewWrite(request,code,thumbnail,usersAuthDTO);
        String title = request.getParameter("title");
        String res = request.getParameter("editordata");

        int reviewNum = 1;
        return "dummypage";
    }

    @PostMapping("/review/comment/write")
    public String reviewCommentWrite(@RequestParam("code")int id, @RequestParam("comment")String comment, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        reviewService.reviewCommentWrite(id,comment,usersAuthDTO);
        return "redirect:/review/"+id;
    }

    @GetMapping("/review/comments/page")
    @ResponseBody
    public JSONArray reviewCommentPage(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return reviewService.reviewCommentPage(request,usersAuthDTO);
    }

    @GetMapping("/review/comment/delete")
    @ResponseBody
    public void reviewCommentdelete(HttpServletRequest request){
        reviewService.reviewCommentDelete(request);
    }

    @GetMapping("/review/up")
    @ResponseBody
    public JSONObject reviewUp(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return reviewService.reviewUp(request, usersAuthDTO);
    }

    @GetMapping("/review/down")
    @ResponseBody
    public JSONObject reviewDown(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return reviewService.reviewDown(request, usersAuthDTO);
    }

    @PostMapping("/review/modify")
    @ResponseBody
    public void reviewModify(HttpServletRequest request){
        reviewService.reviewModify(request);
    }

    @PostMapping("/review/delete")
    @ResponseBody
    public boolean reviewDelete(HttpServletRequest request, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO){
        return reviewService.reviewDelete(request);
    }

    @PostMapping(value = "/review/write/img")
    @ResponseBody
    public String reviewWriteImgUpload(@RequestParam("code")String code, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal UsersAuthDTO usersAuthDTO) throws IOException {
        // 파일이름 랜덤하게
        UUID uuid = UUID.randomUUID();

        // 업로드할 파일 이름
        String str_filename = uuid.toString()+".png";

        String filepath = new File("/reviewimage/"+code+"/"+ usersAuthDTO.getUser_nickname()).getAbsolutePath();

        File f = new File(filepath);
        if (!f.exists()) {
            f.mkdirs();
        }

        f = new File(filepath+"/"+str_filename);

        file.transferTo(f);
        String url = "/reviewimage/"+code+"/"+ usersAuthDTO.getUser_nickname()+"/"+str_filename;
        return url;
    }

    @GetMapping("/reviewimage/{code}/{nickname}/{imgname}")
    public ResponseEntity<byte[]> reviewImage(@PathVariable("code") String code, @PathVariable("nickname")String nickname, @PathVariable("imgname") String imgname){
        InputStream posterStream;
        byte[] imageByteArray = new byte[0];
        try {
            posterStream = new FileInputStream("/reviewimage/"+code+"/"+nickname+"/"+imgname);
            imageByteArray = posterStream.readAllBytes();
            posterStream.close();
        }catch (IOException e){
            try {
                posterStream = new FileInputStream("/reviewimage/default.png");
                imageByteArray = posterStream.readAllBytes();
                posterStream.close();
            }catch (IOException e2){
                log.info("리뷰썸네일 이미지 읽기 에러 : 이미지가 없습니다.");
                log.info(e2.getLocalizedMessage());
            }
        }
        // log.info("이미지 출력");
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }
}
