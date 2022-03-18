package com.naver.man10_49.movie_web.service;

import com.naver.man10_49.movie_web.dto.UsersAuthDTO;
import com.naver.man10_49.movie_web.entity.UsersRequired;
import com.naver.man10_49.movie_web.repository.UsersRequiredRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRequiredRepository usersRequiredRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UsersRequired required = usersRequiredRepository.findByEmail(username);

        if(required==null) {
            throw new UsernameNotFoundException(username);
        }

        UsersAuthDTO usersAuthDTO = new UsersAuthDTO(required.getUser_email(),required.getUser_password(),required.isUser_email_check(),true,true,!required.isUser_banned(),required.getRoleSet().stream().map(usersRole -> new SimpleGrantedAuthority("ROLE_"+usersRole.name())).collect(Collectors.toSet()));
        usersAuthDTO.setUser_nickname(required.getUser_nickname());
        usersAuthDTO.setUser_email(required.getUser_email());
        usersAuthDTO.setUser_gender(required.isUser_gender());
        usersAuthDTO.setUser_birthday(required.getUser_birthday());
        //usersAuthDTO.setUser_photo(required.getUsersOptional().getUser_photo());
        usersAuthDTO.setUser_email_check(required.isUser_email_check());
        return usersAuthDTO;
    }


}
