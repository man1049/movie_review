package kr.co.project.project_tj_sb;

import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.UsersRole;
import kr.co.project.project_tj_sb.entity.UsersOptional;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import kr.co.project.project_tj_sb.repository.UsersOptionalRepository;
import kr.co.project.project_tj_sb.repository.UsersRequiredRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@SpringBootTest
public class UserTest {
    @Autowired
    private UsersRequiredRepository usersRequiredRepository;
    @Autowired
    private UsersOptionalRepository usersOptionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Test
    public void insertUsers(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for(int i = 1 ; i <= 100 ; i++ ){
            UsersRequired usersRequired = null;
            UsersOptional usersOptional = null;
            if(i < 90) {
                usersRequired = UsersRequired.builder()
                        .user_email("man_" + i + "@go.com")
                        .user_password(passwordEncoder.encode("1234"))
                        .user_nickname("가가가" + i)
                        .user_gender(false)
                        .user_birthday(localDate)
                        .user_question("1")
                        .user_answer("1")
                        .user_email_check(true)
                        .build();
                usersOptional = UsersOptional.builder()
                        .user_area1("1")
                        .user_area2("1")
                        .user_area3("1")
                        .user_like1("1")
                        .user_like2("1")
                        .user_like3("1")
                        .required(usersRequired)
                        .build();
                if (i < 80){
                    usersRequired.addUserRole(UsersRole.USER);
                }
                if (80 < i){
                    usersRequired.addUserRole(UsersRole.ADMIN);
                }
            }
            if (90<=i){
                usersRequired = UsersRequired.builder()
                        .user_email("man_" + i + "@go.com")
                        .user_password(passwordEncoder.encode("1234"))
                        .user_nickname("가가가" + i)
                        .user_gender(false)
                        .user_birthday(localDate)
                        .user_question("1")
                        .user_answer("1")
                        .user_email_check(false)
                        .build();
                usersOptional = UsersOptional.builder()
                        .user_area1("1")
                        .user_area2("1")
                        .user_area3("1")
                        .user_like1("1")
                        .user_like2("1")
                        .user_like3("1")
                        .required(usersRequired)
                        .build();
                if (i < 80){
                    usersRequired.addUserRole(UsersRole.USER);
                }
                if (80 < i){
                    usersRequired.addUserRole(UsersRole.ADMIN);
                }
            }
            usersRequiredRepository.save(usersRequired);
            usersOptionalRepository.save(usersOptional);

        }
    }

    //@Test
    public void testRead(){
        //Optional<UsersRequired> urRes = usersRequiredRepository.findByEmail("man_2@go.com");
        //Optional<UsersOptional> uoRes = usersOptionalRepository.findByEmail("man_2@go.com");
       //UsersRequired usersRequired = urRes.get();
       // UsersOptional usersOptional = uoRes.get();
        /*
        UsersAuthDTO usersAuthDTO = new UsersAuthDTO(usersRequired.getUser_email(), usersRequired.getUser_password(),null);
        usersAuthDTO.setUser_email(usersRequired.getUser_email());
        usersAuthDTO.setUser_nickname(usersRequired.getUser_nickname());
        usersAuthDTO.setUser_gender(usersRequired.isUser_gender());
        usersAuthDTO.setUser_birthday(usersRequired.getUser_birthday());
        usersAuthDTO.setUser_photo(usersRequired.getUsersOptional().getUser_photo());
        usersAuthDTO.setUser_area1(usersRequired.getUsersOptional().getUser_area1());
        usersAuthDTO.setUser_area2(usersRequired.getUsersOptional().getUser_area2());
        usersAuthDTO.setUser_area3(usersRequired.getUsersOptional().getUser_area3());
        usersAuthDTO.setUser_area1(usersRequired.getUsersOptional().getUser_area1());
        usersAuthDTO.setUser_area2(usersRequired.getUsersOptional().getUser_area2());
        usersAuthDTO.setUser_area3(usersRequired.getUsersOptional().getUser_area3());
        usersAuthDTO.setUser_email_check(usersRequired.isUser_email_check());
        System.out.println(usersAuthDTO);
        */




       // System.out.println(usersOptional.toString());
    }
}
