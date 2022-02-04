package kr.co.project.project_tj_sb.config;

import antlr.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class LoginFilureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final JavaMailSender emailSender;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        log.info("=============================================================");
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
        } else if (exception instanceof LockedException) {
            errorMessage = "정지 된 계정입니다.";
        } else if (exception instanceof DisabledException){
            errorMessage = "이메일 인증이 되지 않았습니다.";
            /*
            // user_email로 불러와지지 않아서 파라미터를 전부 불러보았다.
            // 파라미터는 폼으로 전송 한 파라미터들이 넘어온다.
            // SecurityConfig에서 email로 설정했으므로 email이 넘어온다.
            String str = request.getParameterNames().nextElement().toString();
            while(str == null){
                log.info("이메일 익센션 파라미터 : "+ str);
                str = request.getParameterNames().nextElement().toString();
            }
            log.info("이메일 파라미터 : "+request.getParameterNames().nextElement());
            */

            /* 로그인 페이지에서 이메일미인증시 재발송
            try{
                MimeMessage message = emailSender.createMimeMessage();

                message.addRecipients(Message.RecipientType.TO, request.getParameter("email"));//받는사람
                message.setSubject("도전점심 회원가입 이메일 인증");//제목

                String msgg="";
                msgg+= "<div style='margin:100px;'>";
                msgg+= "<h1> 안녕하세요"+request.getParameter("email")+"님 도전점심입니다. </h1>";
                msgg+= "<br>";
                msgg+= "<p>아래 버튼을 클릭하여 이메일 인증을 해주세요<p>";
                msgg+= "<br>";
                msgg+= "<a href=\"http://localhost:8080/join/authentication\"></a>";
                msgg+= "<form method=\"post\" action=\"http://localhost:8080/join/authentication\">"+
                        "<input type=\"hidden\" name=\"user_email\" value=\""+request.getParameter("email")+"\">"+
                        "<input type=\"submit\" value=\"인증확인\">"+
                        "</form><br>";

                msgg+= "<br>";
                message.setText(msgg, "utf-8", "html");//내용
                message.setFrom(new InternetAddress("tkdalswoals1046@gmail.com","도전점심"));//보내는 사람

                emailSender.send(message);

            }catch (Exception e){
                log.info("인증메일 전송 exception : "+e.getLocalizedMessage());
                throw new IllegalArgumentException();
            }
            */
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }
        errorMessage = URLEncoder.encode(errorMessage,"UTF-8");
        setDefaultFailureUrl("/?exception="+errorMessage);
        super.onAuthenticationFailure(request, response, exception);

    }
}
