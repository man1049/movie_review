package kr.co.project.project_tj_sb.service;

import kr.co.project.project_tj_sb.dto.UsersAuthDTO;
import kr.co.project.project_tj_sb.entity.UsersRequired;
import kr.co.project.project_tj_sb.repository.UsersRequiredRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersRequiredRepository usersRequiredRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            log.info("아이디 : "+username);
        UsersRequired required = usersRequiredRepository.findByEmail(username);

        if(required==null) {
            throw new UsernameNotFoundException(username);
        }

        log.info("=============");
        log.info(required);

        UsersAuthDTO usersAuthDTO = new UsersAuthDTO(required.getUser_email(),required.getUser_password(),required.getRoleSet().stream().map(usersRole -> new SimpleGrantedAuthority("ROLE_"+usersRole.name())).collect(Collectors.toSet()));
        usersAuthDTO.setUser_nickname(required.getUser_nickname());
        usersAuthDTO.setUser_email(required.getUser_email());
        usersAuthDTO.setUser_gender(required.isUser_gender());
        usersAuthDTO.setUser_birthday(required.getUser_birthday());
        usersAuthDTO.setUser_photo(required.getUsersOptional().getUser_photo());
        usersAuthDTO.setUser_area1(required.getUsersOptional().getUser_area1());
        usersAuthDTO.setUser_area2(required.getUsersOptional().getUser_area2());
        usersAuthDTO.setUser_area3(required.getUsersOptional().getUser_area3());
        usersAuthDTO.setUser_area1(required.getUsersOptional().getUser_area1());
        usersAuthDTO.setUser_area2(required.getUsersOptional().getUser_area2());
        usersAuthDTO.setUser_area3(required.getUsersOptional().getUser_area3());
        usersAuthDTO.setUser_email_check(required.isUser_email_check());
        return usersAuthDTO;
    }


}
