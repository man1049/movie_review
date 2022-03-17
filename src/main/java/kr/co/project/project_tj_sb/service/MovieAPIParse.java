package kr.co.project.project_tj_sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieAPIParse {
    /*
    // 영화진흥위원회 오픈API 일일박스오피스 파싱
    public JSONArray movieTop10(){
        try{
            // 인증키
            String serviceKey = "ceabdcb6d52d7eb5709bbb09dc253b97";
            long ctm = System.currentTimeMillis()-86400000; // 하루 이전의 데이터를 가져와야하므로 밀리초단위로 하루를 빼줌
            SimpleDateFormat foramt = new SimpleDateFormat("yyyyMMdd");
            String nowday = foramt.format(new Date(ctm));

            log.info(nowday);

            String urlStr = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
            urlStr += "?"+ URLEncoder.encode("key","UTF-8") +"=" + serviceKey;
            urlStr += "&"+ URLEncoder.encode("targetDt","UTF-8")+"=" + nowday;

            URL url = new URL(urlStr);

            String line = "";
            String result = "";

            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                result = result.concat(line);
                log.info(line);
            }

            // JSON parser 만들어 문자열 데이터를 객체화
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(result);

            JSONObject jsonObject = (JSONObject)obj.get("boxOfficeResult");
            JSONArray jsonArray = (JSONArray) jsonObject.get("dailyBoxOfficeList");

            String miseType = "";

            JSONArray top10 = new JSONArray();

            for (int i=1;i< jsonArray.size()+1;i++) {
                JSONObject top10json = (JSONObject) jsonArray.get(i);
                JSONObject movies = new JSONObject();
                movies.put("rank",(String) top10json.get("rank"));   // 랭킹
                movies.put("movieNm",(String) top10json.get("movieNm"));    // 영화이름(한글)
                movies.put("openDt",(String) top10json.get("openDt"));     // 영화 개봉일
                movies.put("audiCnt",(String) top10json.get("audiCnt"));    // 당일 관객수
                List<String> detail = moviedetail((String)top10json.get("movieCd"));
                movies.put("movieCd",detail.get(0));
                movies.put("movieNm",detail.get(1));
                movies.put("movieNmEn",detail.get(2));
                movies.put("showTm",detail.get(3));
                movies.put("openDt",detail.get(4));
                movies.put("prdStatNm",detail.get(5));
                movies.put("genreNm",detail.get(6));
                movies.put("peopleNm",detail.get(7));
                movies.put("watchGradeNm",detail.get(8));

                top10.add(movies);
            }
            br.close();
            return top10;
        } catch (Exception e) {
            log.info("top10 파싱익셉션 : "+e.getLocalizedMessage());
            return null;
        }
    }

    public List<String> moviedetail(String movieCd){
        try{
            // 인증키
            String serviceKey = "ceabdcb6d52d7eb5709bbb09dc253b97";

            String urlStr = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
            urlStr += "?"+ URLEncoder.encode("key","UTF-8") +"=" + serviceKey;
            urlStr += "&"+ URLEncoder.encode("movieCd","UTF-8")+"=" + movieCd;

            URL url = new URL(urlStr);

            String line = "";
            String result = "";

            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                result = result.concat(line);
                log.info(line);
            }
            List<String> list = new ArrayList<>();
            // JSON parser 만들어 문자열 데이터를 객체화한다.
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(result);

            JSONObject jsonObject = (JSONObject)obj.get("movieInfoResult");
            JSONObject detail = (JSONObject) jsonObject.get("movieInfo");

            list.add((String) detail.get("movieCd"));   // 영화 코드
            list.add((String) detail.get("movieNm"));    // 영화이름(한글)
            list.add((String) detail.get("movieNmEn"));    // 영화이름(영어)

            int time = Integer.parseInt((String) detail.get("showTm"));    // 상영시간
            if(time >= 60){
                list.add((time/60)+"시간 "+(time%60)+"분");
            }else{
                list.add((time%60)+"분");
            }
            try {
                list.add((String) detail.get("openDt"));     // 개봉일
            }catch (Exception e){
                list.add("정보 없음");
            }
            list.add((String) detail.get("prdtStatNm")); // 제작상태

            try {
                JSONArray genres = (JSONArray) detail.get("genres"); // 장르 - 배열로 되어있음
                JSONObject genreNm = (JSONObject) genres.get(0);
                list.add((String) genreNm.get("genreNm"));    // 장르
            }catch (Exception e){
                list.add("정보 없음");
            }

            try {
                JSONArray nations = (JSONArray) detail.get("nations"); // 제작국가 - 배열로 되어있음
                JSONObject nationNm = (JSONObject) nations.get(0);
                list.add((String) nationNm.get("nationNm"));   // 제작국가
            }catch (Exception e){
                list.add("정보 없음");
            }

            try {
                JSONArray directors = (JSONArray) detail.get("directors"); // 감독 정보 - 배열로 되어있음
                JSONObject peopleNm = (JSONObject) directors.get(0);
                list.add((String) peopleNm.get("peopleNm"));    // 감독
            }catch (Exception e){
                list.add("정보 없음");
            }
            try {
                JSONArray audits = (JSONArray) detail.get("audits"); // 심의 정보 - 배열로 되어있음
                JSONObject watchGradeNm = (JSONObject) audits.get(0);
                list.add((String) watchGradeNm.get("watchGradeNm"));    // 심의 정보
            }catch (Exception e){
                list.add("정보 없음");
            }

            log.info("디테일 : "+detail.toString());
            String miseType = "";
            log.info("리스트"+list.toString());
            br.close();
            return list;
        } catch (Exception e) {
            log.info("디테일 파싱익셉션 : "+e.getLocalizedMessage());
            return null;
        }
    }

    public List<String> movieList(String num){
        try{
            // 인증키
            String serviceKey = "ceabdcb6d52d7eb5709bbb09dc253b97";

            String urlStr = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
            urlStr += "?"+ URLEncoder.encode("key","UTF-8") +"=" + serviceKey;
            urlStr += "&"+ URLEncoder.encode("curPage","UTF-8")+"=" + num;

            URL url = new URL(urlStr);

            String line = "";
            String result = "";

            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                result = result.concat(line);
                log.info(line);
            }
            List<String> list = new ArrayList<>();
            // JSON parser 만들어 문자열 데이터를 객체화한다.
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(result);

            JSONObject jsonObject = (JSONObject)obj.get("movieListResult");
            JSONArray jsonArray = (JSONArray)jsonObject.get("movieList");

            for(int i = 0; i < jsonArray.size(); i++){
                JSONObject movieList = (JSONObject) jsonArray.get(i);


            }
            return null;
        } catch (Exception e) {
            log.info("디테일 파싱익셉션 : "+e.getLocalizedMessage());
            return null;
        }
    }
    */
}
