package kr.co.project.project_tj_sb.service;

import kr.co.project.project_tj_sb.entity.MovieComment;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import kr.co.project.project_tj_sb.repository.MovieDetaileRepository;
import kr.co.project.project_tj_sb.repository.UsersRequiredRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieDetailServiceImpl implements MovieDetaileService {

    private final MovieDetaileRepository detaileRepository;
    private final UsersRequiredRepository requiredRepository;

    @Override
    @Transactional
    public void commentWrite(HttpServletRequest request, Principal principal) {
        int star = Integer.parseInt(request.getParameter("star"));
        String comment = request.getParameter("comment");
        String code = request.getParameter("code");
        String email = principal.getName();

        UsersRequired required = requiredRepository.findByEmail(email);
        String nickname = required.getUser_nickname();
        
        // 현재 년도를 가져오기
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int nowTime = Integer.parseInt(sdf.format(timestamp));
        
        // 작성자의 생일을 불러와서 연도만 추출
        LocalDate userBirthday = required.getUser_birthday();
        int userYear = userBirthday.getYear();

        // 현재시간과 생년을 빼면 실제나이-1 이 나오므로 +1을 해줌
        int age = nowTime-userYear+1;

        MovieComment mc = MovieComment.builder()
                .movie_code(code)
                .movie_comment(comment)
                .movie_star(star)
                .user_gender(required.isUser_gender())
                .user_nickname(nickname)
                .user_age(age)
                .build();

        detaileRepository.save(mc);

    }

    @Override
    public JSONObject getCommentsInfo(HttpServletRequest request) {
        log.info("================ getComments ================");
        String code = request.getParameter("code");
        log.info("code="+code);
        List<MovieComment> list = detaileRepository.findByMovie_codeOrderByIdAsc(code);
        log.info(list.toString());

        JSONObject comments_info = new JSONObject();
        JSONArray comments = new JSONArray();
        JSONObject ages = new JSONObject();
        JSONObject stars = new JSONObject();

        int teenager = 0;
        int twenties = 0;
        int thirties = 0;
        int forties = 0;
        int fifties_later = 0;

        int star_1 = 0;
        int star_2 = 0;
        int star_3 = 0;
        int star_4 = 0;
        int star_5 = 0;

        int man = 0;
        int woman = 0;
        int star_count = 0;
        double star_all = 0;

        int count = 0;

        for (MovieComment mc : list){
            int age = mc.getUser_age();
            double star = mc.getMovie_star();

            star_count++;
            star_all = star_all + mc.getMovie_star();

            if(age < 20){
                teenager++;
            }else if(age < 30){
                twenties++;
            }else if(age < 40){
                thirties++;
            }else if(age < 50){
                forties++;
            }else{
                fifties_later++;
            }

            switch ((int) star){
                case 1 : star_1++; break;
                case 2 : star_2++; break;
                case 3 : star_3++; break;
                case 4 : star_4++; break;
                case 5 : star_5++; break;
            }

            if(mc.isUser_gender() == false){
                man++;
            }else{
                woman++;
            }
        }

        ages.put("a10",teenager);
        ages.put("a20",twenties);
        ages.put("a30",thirties);
        ages.put("a40",forties);
        ages.put("other",fifties_later);

        stars.put("s1",star_1);
        stars.put("s2",star_2);
        stars.put("s3",star_3);
        stars.put("s4",star_4);
        stars.put("s5",star_5);


        for(MovieComment mc : list){
            JSONObject comment = new JSONObject();
            comment.put("text",mc.getMovie_comment());
            comment.put("nickname",mc.getUser_nickname());
            comment.put("star",mc.getMovie_star());

            comments.add(comment);
            count++;
            if(count > 14){
                break;
            }
        }

        double star_avg = (double) Math.floor((star_all/star_count)*10)/10;
        comments_info.put("woman",woman);
        comments_info.put("man",man);
        comments_info.put("star_avg",star_avg);
        comments_info.put("comments",comments);
        comments_info.put("stars",stars);
        comments_info.put("ages",ages);
        /*
        comments_info.put("teenager",teenager);
        comments_info.put("twenties",twenties);
        comments_info.put("thirties",thirties);
        comments_info.put("forties",forties);
        comments_info.put("fifties_later",fifties_later);
        */

        log.info(comments_info);

        return comments_info;
    }

    @Override
    public boolean isAlreadyCommentWrite(HttpServletRequest request,Principal principal) {

        UsersRequired required = requiredRepository.findByEmail(principal.getName());
        String user_nickname = required.getUser_nickname();
        String movie_code = request.getParameter("code");

        int count = detaileRepository.countByUser_email(user_nickname, movie_code);

        if (count > 0) {
            log.info("true입니다");
            return true;
        } else {
            log.info("false입니다");
            return false;
        }

    }

    @Override
    public JSONArray getCommentsPage(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        String code = request.getParameter("code");

        Pageable pageable = PageRequest.of(page,size);

        List<MovieComment> list = detaileRepository.findByMovie_codeOrderByIdAsc(code,pageable);

        JSONArray jsonArray = new JSONArray();

        for (MovieComment comment : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("star",comment.getMovie_star());
            jsonObject.put("comment",comment.getMovie_comment());
            jsonObject.put("nickname",comment.getUser_nickname());

            jsonArray.add(jsonObject);
        }

        log.info(jsonArray);

        return jsonArray;
    }
}
