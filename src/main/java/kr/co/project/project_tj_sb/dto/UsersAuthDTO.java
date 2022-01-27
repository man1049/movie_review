package kr.co.project.project_tj_sb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Getter
@Setter
@ToString
public class UsersAuthDTO extends User {
    private String user_email;
    private String user_nickname;
    private boolean user_gender;
    private LocalDate user_birthday;
    /////////////////////////////////
    private String user_photo;
    private String user_area1;
    private String user_area2;
    private String user_area3;
    private String user_like1;
    private String user_like2;
    private String user_like3;
    private boolean user_email_check;


    public UsersAuthDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.user_email = username;

    }


}
