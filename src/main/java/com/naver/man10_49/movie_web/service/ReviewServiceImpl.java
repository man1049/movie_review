package com.naver.man10_49.movie_web.service;

import com.naver.man10_49.movie_web.dto.UsersAuthDTO;
import com.naver.man10_49.movie_web.entity.Review;
import com.naver.man10_49.movie_web.entity.ReviewComment;
import com.naver.man10_49.movie_web.entity.ReviewUpDown;
import com.naver.man10_49.movie_web.entity.UsersRequired;
import com.naver.man10_49.movie_web.repository.ReviewCommentRepository;
import com.naver.man10_49.movie_web.repository.ReviewRepository;
import com.naver.man10_49.movie_web.repository.ReviewUpDownRepository;
import com.naver.man10_49.movie_web.repository.UsersRequiredRepository;
import com.naver.man10_49.movie_web.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UsersRequiredRepository usersRequiredRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewUpDownRepository reviewUpDownRepository;

    @Override
    public void reviewWrite(HttpServletRequest request, String code, MultipartFile thumbnail, UsersAuthDTO usersAuthDTO) throws IOException {

        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString()+".png";
        String content = request.getParameter("editordata");
        String title = request.getParameter("title");
        String movieName = request.getParameter("moviename");
        int star = Integer.parseInt(request.getParameter("star"));

        String url = null;

        if(!thumbnail.isEmpty()) {
            String path = new File("/reviewimage/" + code + "/" + usersAuthDTO.getUser_nickname()).getAbsolutePath();
            File f = new File(path);

            if (!f.exists()) {
                f.mkdirs();
            }

            f = new File(path + "/" + filename);
            thumbnail.transferTo(f);
            url = "/reviewimage/" + code + "/" + usersAuthDTO.getUser_nickname() + "/" + filename;
        }else{
            url = "/reviewimage/" + code + "/" + usersAuthDTO.getUser_nickname() + "/default.png";
        }

        UsersRequired usersRequired = usersRequiredRepository.findByEmail(usersAuthDTO.getUser_email());

        Review review = Review.builder()
                .movie_code(code)
                .thumbnail(url)
                .review_title(title)
                .review_content(content)
                .usersRequired(usersRequired)
                .review_star(star)
                .movie_name(movieName)
                .build();

        reviewRepository.save(review);
    }

    @Override
    public void reviewModify(HttpServletRequest request) {
        String review_content = request.getParameter("content");
        int review_num = Integer.parseInt(request.getParameter("code"));
        LocalDate localDate = LocalDate.now();
        reviewRepository.updateReviewContent(review_num,review_content,localDate);
    }

    @Override
    public boolean reviewDelete(HttpServletRequest request) {
        log.error("딜리트 진입 =====================================");

        int review_num = Integer.parseInt(request.getParameter("code"));

        List<ReviewComment> reviewCommentList = reviewCommentRepository.findByReview_num(review_num);
        for(ReviewComment rc : reviewCommentList){
            reviewCommentRepository.deleteById(rc.getId());
        }

        List<ReviewUpDown> reviewUpDownList = reviewUpDownRepository.findByReview_num(review_num);
        for(ReviewUpDown rud : reviewUpDownList){
            reviewUpDownRepository.deleteById(rud.getId());
        }

        reviewRepository.deleteById(review_num);
        return true;
    }

    @Override
    public List<Map<String,Object>> reviewListPage() {
        int size = 20;
        int page = 0;
        Pageable pageable = PageRequest.of(page,size);
        List<Review> list = reviewRepository.findAllOrderById(pageable);


        List<Map<String,Object>> reviews = new ArrayList<>();

        for(Review rv : list){
            Map<String,Object> review = new HashMap<>();

            review.put("title",rv.getReview_title());
            review.put("thumbnail",rv.getThumbnail());
            review.put("nickname",rv.getUsersRequired().getUser_nickname());
            review.put("star",rv.getReview_star());
            review.put("num",rv.getId());
            review.put("moviename",rv.getMovie_name());
            review.put("up",rv.getReview_up());

            reviews.add(review);
        }
        return reviews;
    }

    @Override
    public JSONArray reviewListPage(int page) {
        int size = 20;
        Pageable pageable = PageRequest.of(page,size);
        List<Review> list = reviewRepository.findAllOrderById(pageable);

        JSONArray jsonArray = new JSONArray();

        for(Review rv : list){

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title",rv.getReview_title());
            jsonObject.put("thumbnail",rv.getThumbnail());
            jsonObject.put("nickname",rv.getUsersRequired().getUser_nickname());
            jsonObject.put("star",rv.getReview_star());
            jsonObject.put("num",rv.getId());
            jsonObject.put("moviename",rv.getMovie_name());
            jsonObject.put("up",rv.getReview_up());

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public long getPage() {
        long res = reviewRepository.count();
        return res;
    }

    @Override
    public void reviewCommentWrite(int id, String comment, UsersAuthDTO usersAuthDTO) {

        UsersRequired required = usersRequiredRepository.findByEmail(usersAuthDTO.getUser_email());

        ReviewComment reviewComment = ReviewComment.builder()
                .review_num(id)
                .comment(comment)
                .usersRequired(required)
                .build();

        reviewCommentRepository.save(reviewComment);
    }

    @Override
    public JSONArray reviewCommentPage(HttpServletRequest request, UsersAuthDTO usersAuthDTO) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        String nickname = request.getParameter("nickname");
        int code = Integer.parseInt(request.getParameter("code"));

        Pageable pageable = PageRequest.of(page,size);

        List<ReviewComment> list = reviewCommentRepository.findByReview_num(code,pageable);

        JSONArray jsonArray = new JSONArray();

        for (ReviewComment comment : list){
            JSONObject jsonObject = new JSONObject();
            UsersRequired usersRequired = comment.getUsersRequired();

            jsonObject.put("comment",comment.getComment());
            jsonObject.put("nickname",usersRequired.getUser_nickname());
            boolean myCommentFlag = usersRequired.getUser_nickname().equals(nickname);
            if(myCommentFlag || usersAuthDTO.getAuthorities().size() > 1){
                jsonObject.put("mycomment",true);
                jsonObject.put("id",comment.getId());
            }else{
                jsonObject.put("mycomment",false);
            }

            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    @Override
    public void reviewCommentDelete(HttpServletRequest request) {
        reviewCommentRepository.deleteById(Integer.parseInt(request.getParameter("id")));
    }

    @Override
    public JSONObject reviewUp(HttpServletRequest request, UsersAuthDTO usersAuthDTO) {
        int review_num = Integer.parseInt(request.getParameter("code"));
        JSONObject jsonObject = new JSONObject();

        int count = reviewUpDownRepository
                .countReviewUpDownByNicknameAndReview_num(
                        usersAuthDTO.getUser_nickname()
                        ,review_num);

        if(count > 0){
            jsonObject.put("res",false);
            return jsonObject;
        }else{
            reviewRepository.updateReviewUp(review_num);

            ReviewUpDown upDown = ReviewUpDown.builder()
                    .nickname(usersAuthDTO.getUser_nickname())
                    .review_num(review_num)
                    .build();

            reviewUpDownRepository.save(upDown);

            Review review = reviewRepository.getById(review_num);

            jsonObject.put("res",true);
            jsonObject.put("upcount",review.getReview_up());
            jsonObject.put("downcount",review.getReview_down());
            return jsonObject;
        }
    }

    @Override
    public JSONObject reviewDown(HttpServletRequest request, UsersAuthDTO usersAuthDTO) {
        int review_num = Integer.parseInt(request.getParameter("code"));
        JSONObject jsonObject = new JSONObject();

        int count = reviewUpDownRepository
                .countReviewUpDownByNicknameAndReview_num(
                        usersAuthDTO.getUser_nickname()
                        ,review_num);

        if(count > 0){
            jsonObject.put("res",false);
            return jsonObject;
        }else{
            reviewRepository.updateReviewDown(review_num);

            ReviewUpDown upDown = ReviewUpDown.builder()
                    .nickname(usersAuthDTO.getUser_nickname())
                    .review_num(review_num)
                    .build();

            reviewUpDownRepository.save(upDown);

            Review review = reviewRepository.getById(review_num);

            jsonObject.put("res",true);
            jsonObject.put("upcount",review.getReview_up());
            jsonObject.put("downcount",review.getReview_down());
            return jsonObject;
        }
    }

    @Override
    public Map<String,Object> getReview(int id) {
        StringBuilder sb = new StringBuilder();
        JSONObject jsonObject = null;
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> movie = new HashMap<>();
        String movieCd; // 영화 코드
        String movieNm; // 영화명
        String dr; // 감독
        String gen; // 장르
        String gr; // 심의

        Review review = reviewRepository.getById(id);
        String key = "ceabdcb6d52d7eb5709bbb09dc253b97";
        String code = review.getMovie_code();

        try {
            URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key="+key+"&movieCd="+code);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while(br.ready()){
                sb.append(br.readLine());
            }
            br.close();
        } catch (Exception e){
            log.error("getReview API : 연결실패 또는 읽어오기 실패");
            log.error(e.getLocalizedMessage());
        }
        try {
            jsonObject = (JSONObject) new JSONParser().parse(sb.toString());
        }catch (Exception e){
            log.error("getReview API : JSON 파싱 실패");
            log.error(e.getLocalizedMessage());
        }

        JSONObject json = (JSONObject) jsonObject.get("movieInfoResult");
        JSONObject json2 = (JSONObject) json.get("movieInfo");



        movieNm = (String) json2.get("movieNm");
        movieCd = (String) json2.get("movieCd");

        JSONArray drAr = (JSONArray) json2.get("directors");
        if(drAr.size() == 0){
            dr = "정보없음";
        }else{
            JSONObject drArJs = (JSONObject) drAr.get(0);
            dr = (String) drArJs.get("peopleNm");
        }

        JSONArray genAr = (JSONArray) json2.get("genres");
        if(genAr.size() == 0){
            gen = "정보없음";
        }else{
            JSONObject genArJs = (JSONObject) genAr.get(0);
            gen = (String) genArJs.get("genreNm");
        }

        JSONArray grAr = (JSONArray) json2.get("audits");
        if(genAr.size() == 0){
            gr = "정보없음";
        }else{
            JSONObject grArJs = (JSONObject) grAr.get(0);
            gr = (String) grArJs.get("watchGradeNm");
        }

        /*
        let watchGrade
        try{
            watchGrade = mv.audits[0].watchGradeNm
        }catch (e) {
            watchGrade = "정보없음"
        }
        */

        movie.put("director",dr);
        movie.put("moviename",movieNm);
        movie.put("moviecode",movieCd);
        movie.put("genres",gen);
        movie.put("watchgrade",gr);


        data.put("movie",movie);
        data.put("review",review);


        return data;
    }
}
