package kr.co.project.project_tj_sb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginFilureHandler loginFilureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //암호화해서 평문과 비교는 할 수 있지만 복호화는 할 수 없는
        //클래스의 인스턴스를 생성해서 리턴
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/font/**","/images/**","/video/**","/resources/**","/static/**","resources/**","static/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/logingo").permitAll()
                    .antMatchers("/join/authentication").permitAll()
                    .antMatchers("/pwchange").permitAll()
                    .antMatchers("/main").hasRole("USER")
                    .antMatchers("/detaile").hasRole("USER")
                    .antMatchers("/detaile/**").hasRole("USER")
                    .antMatchers("/movielist").hasRole("USER")
                    .antMatchers("/main/**").hasRole("USER")
                    .antMatchers("/images/**").hasRole("USER")
                    .antMatchers("/css/**").hasRole("USER")
                    .antMatchers("/js/**").hasRole("USER")
                    .antMatchers("/video/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/logingo").permitAll().loginProcessingUrl("/login")
                    .usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/main")
                    .failureHandler(loginFilureHandler) // 로그인 실패 시 진행될 Handler
                //.successHandler() // 로그인 성공 시 진행될 Handler
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .csrf().disable();
        /*
        httpSecurity
                .sessionManagement()
                .maximumSessions(1) // 최대 허용 가능 세션 수
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단
                .expiredUrl("/");

         */
    }
}

