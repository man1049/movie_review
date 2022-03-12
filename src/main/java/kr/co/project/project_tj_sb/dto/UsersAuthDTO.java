package kr.co.project.project_tj_sb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Log4j2
@Getter
@Setter
@ToString
public class UsersAuthDTO extends User{
    private String user_email;
    private String user_nickname;
    private boolean user_gender;
    private LocalDate user_birthday;
    /////////////////////////////////
    private boolean user_email_check;


    public UsersAuthDTO(String username, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);

        // accountNonLocked는 정지 여부이다.
        // enabled는 이메일 인증여부이다.
    }

}
