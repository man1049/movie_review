package kr.co.project.project_tj_sb.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        //암호화해서 평문과 비교는 할 수 있지만 복호화는 할 수 없는
        //클래스의 인스턴스를 생성해서 리턴
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeRequests().antMatchers("/logingo").permitAll()
                .antMatchers("/main").hasRole("USER")
                .and()
                .formLogin().loginPage("/logingo").loginProcessingUrl("/login")
                .and()
                .formLogin().usernameParameter("email").defaultSuccessUrl("/main")
                .and()
                .csrf().disable();
    }
}

