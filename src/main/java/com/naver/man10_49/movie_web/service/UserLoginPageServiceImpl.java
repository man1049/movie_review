package com.naver.man10_49.movie_web.service;

import com.naver.man10_49.movie_web.dto.UserDTO;
import com.naver.man10_49.movie_web.repository.UsersRequiredRepository;
import com.naver.man10_49.movie_web.config.EmailConfig;
//import kr.co.project.project_tj_sb.entity.UsersOptional;
import com.naver.man10_49.movie_web.entity.UsersRequired;
//import kr.co.project.project_tj_sb.repository.UsersOptionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserLoginPageServiceImpl implements UserLoginPageService {

    private final UsersRequiredRepository usersRequiredRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfig emailConfig;
    
    //비밀번호 변경
    @Override
    public boolean pwChange(HttpServletRequest httpServletRequest){
        String code = httpServletRequest.getParameter("code");
        String user_password = passwordEncoder.encode(httpServletRequest.getParameter("user_password"));
        String user_email = null;
        try {
            String undecode = new String(Base64.getDecoder().decode(code), "UTF-8");
            undecode = new String(Base64.getDecoder().decode(undecode), "UTF-8");
            user_email = new String(Base64.getDecoder().decode(undecode), "UTF-8");
        }catch (Exception e){
            log.info("비밀번호 변경페이지 디코드 에러 : "+e.getLocalizedMessage());
            return false;
        }

        LocalDate user_change_password_date = LocalDate.now();

        int updateSucsses = usersRequiredRepository.updateUser_password(user_email,user_password,user_change_password_date);

        if(updateSucsses > 0){
            return true;
        }else{
            return false;
        }
    }

    //비밀번호 변경 페이지 ajax
    public boolean pwChangePage(HttpServletRequest httpServletRequest){

        String code = httpServletRequest.getParameterNames().nextElement();
        log.info("code : "+code);
        String decode = null;
        try {
             String undecode = new String(Base64.getDecoder().decode(code), "UTF-8");
             undecode = new String(Base64.getDecoder().decode(undecode), "UTF-8");
             decode = new String(Base64.getDecoder().decode(undecode), "UTF-8");
             log.info("디코드 : "+decode);
        }catch (Exception e){
            log.info("비밀번호 변경페이지 디코드 에러 : "+e.getLocalizedMessage());
            return false;
        }

        int checkByEmail = usersRequiredRepository.countByUser_email(decode);
        if(checkByEmail > 0){
            return true;
        }else{
            return false;
        }
    };

    //비밀번호 변경메일 보내기

    @Override
    public boolean findPW(HttpServletRequest httpServletRequest){
        String user_email = httpServletRequest.getParameter("user_email");
        String user_question = httpServletRequest.getParameter("user_question");
        String user_answer = httpServletRequest.getParameter("user_answer");


        int check = usersRequiredRepository.findByUser_emailAndUser_questionAndUser_answer(user_email,user_question,user_answer);
        String user_nickname = usersRequiredRepository.findByUser_nickname(user_email);
        String user_emailEncorder = new String(Base64.getEncoder().encodeToString(user_email.getBytes()));
        String user_emailEncorder2 = new String(Base64.getEncoder().encodeToString(user_emailEncorder.getBytes()));
        String user_emailEncorder3 = new String(Base64.getEncoder().encodeToString(user_emailEncorder2.getBytes()));

        if(check > 0){
            try{
                MimeMessage message = emailConfig.javaMailService().createMimeMessage();

                message.addRecipients(Message.RecipientType.TO, user_email);//받는사람
                message.setSubject("도전점심 비밀번호 변경");//제목

                String msgg="";
                msgg+= "<div style='margin:100px;'>";
                msgg+= "<h1> 안녕하세요"+user_nickname+"님 도전점심입니다. </h1>";
                msgg+= "<br>";
                msgg+= "<p>아래 버튼을 클릭하여 비밀번호를 변경해주세요.<p>";
                msgg+= "<br>";
                msgg+= "<form method=\"get\" action=\"http://118.32.35.166:1029/pwchange\">"+
                        "<input type=\"hidden\" name=\"code\" value=\""+user_emailEncorder3+"\">"+
                        "<input type=\"submit\" value=\"비밀번호 변경하기\">"+
                        "</form><br>";
                msgg+= "<br>";
                message.setText(msgg, "utf-8", "html");//내용
                message.setFrom(new InternetAddress("tkdalswoals1046@gmail.com","도전점심"));//보내는 사람

                emailConfig.javaMailService().send(message);

            }catch (Exception e){
                log.info("인증메일 전송 exception : "+e.getLocalizedMessage());
                throw new IllegalArgumentException();
            }
            return true;
        }else{
            return false;
        }
    }
    
    //이메일 중복검사
    @Override
    public boolean emailCheck(HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getParameterNames().nextElement();
        boolean flag = false;
        int count = usersRequiredRepository.countByUser_email(email);

        if(count > 0){
            flag = true;
        }

        return flag;
    }

    //닉네임 중복검사
    @Override
    public boolean nicknameCheck(HttpServletRequest httpServletRequest) {
        String nickname = httpServletRequest.getParameterNames().nextElement();
        boolean flag = false;
        int count = usersRequiredRepository.countByUser_nickname(nickname);

        if(count > 0){
            flag = true;
        }

        return flag;
    }
    
    
    //이메일 인증완료
    @Override
    public boolean authentication(String user_email) {

        if(!usersRequiredRepository.getByUser_email_check(user_email)){
            usersRequiredRepository.updateUserEmailCheck(user_email);
            return false;
        }else {
            return true;
        }
    }

    // 회원가입 폼의 데이터를 가져와서 userJoinCheck을 호출
    // 중복확인은 폼에 데이터를 입력시 미리 필터링을 해서 괜찮음
    @Override
    public void userJoin(HttpServletRequest httpServletRequest) {

        Boolean gender;
        LocalDate localDate;
        
        // 주민번호 뒷자리 첫번째자리로 성별을 판단
        // javascript로 1~4 이외의 숫자는 막아놓았음
        if(httpServletRequest.getParameter("user_gender").equals("1") || httpServletRequest.getParameter("user_gender").equals("3")){
            gender = false;
        }else{
            gender = true;
        }
        
        // 뒷자리로 1900년도인지 2000년도인지 판별
        // javascript로 현재년도-100년~현재년도까지만 입력 가능하도록 해놓았음
        if(httpServletRequest.getParameter("user_gender").equals("1") || httpServletRequest.getParameter("user_gender").equals("2")){
            String yearmonthday = httpServletRequest.getParameter("user_yearmonthday");
            localDate = LocalDate.of(
                    1900+Integer.parseInt(yearmonthday.substring(0,2)),
                    Integer.parseInt(yearmonthday.substring(2,4)),
                    Integer.parseInt(yearmonthday.substring(4,6)));
        }else{
            String yearmonthday = httpServletRequest.getParameter("user_yearmonthday");
            localDate = LocalDate.of(
                    2000+Integer.parseInt(yearmonthday.substring(0,2)),
                    Integer.parseInt(yearmonthday.substring(2,4)),
                    Integer.parseInt(yearmonthday.substring(4,6)));
        }

        LocalDate localDateNow = LocalDate.now();
        UserDTO userDTO = UserDTO.builder()
                .user_email(httpServletRequest.getParameter("user_email"))
                .user_password(passwordEncoder.encode(httpServletRequest.getParameter("user_password1")))
                .user_nickname(httpServletRequest.getParameter("user_nickname"))
                .user_gender(gender)
                .user_birthday(localDate)
                .user_question(httpServletRequest.getParameter("user_question"))
                .user_answer(httpServletRequest.getParameter("user_answer"))
                .user_change_password_date(localDateNow)
                .user_join_date(localDateNow)
                .user_email_check(false)
                .build();

        UsersRequired usersRequired = dtoToEntity(userDTO);

        try{
            MimeMessage message = emailConfig.javaMailService().createMimeMessage();


            message.addRecipients(Message.RecipientType.TO, usersRequired.getUser_email());//받는사람
            message.setSubject("영화는 영화다 회원가입 이메일 인증");//제목

            String msgg="";
            msgg+= "<div style='margin:100px;'>";
            msgg+= "<h1> 안녕하세요"+usersRequired.getUser_nickname()+"님 영화는 영화다입니다. </h1>";
            msgg+= "<br>";
            msgg+= "<p>아래 버튼을 클릭하여 이메일 인증을 해주세요<p>";
            msgg+= "<br>";
            msgg+= "<form method=\"post\" action=\"http://118.32.35.166:1029/join/authentication\">"+
                    "<input type=\"hidden\" name=\"user_email\" value=\""+usersRequired.getUser_email()+"\">"+
                    "<input type=\"submit\" value=\"인증확인\">"+
                    "</form><br>";

            msgg+= "<br>";
            message.setText(msgg, "utf-8", "html");//내용
            message.setFrom(new InternetAddress("tkdalswoals1046@gmail.com","영화는 영화다"));//보내는 사람

            emailConfig.javaMailService().send(message);

        }catch (Exception e){
            log.info("인증메일 전송 exception : "+e.getLocalizedMessage());
            throw new IllegalArgumentException();
        }

        usersRequiredRepository.save(usersRequired);
    }
}
