package com.ItsTime.ItNovation.service.user;

import com.ItsTime.ItNovation.common.dto.ApiResult;
import com.ItsTime.ItNovation.domain.user.Role;
import com.ItsTime.ItNovation.domain.user.User;
import com.ItsTime.ItNovation.domain.user.UserRepository;

import com.ItsTime.ItNovation.domain.user.dto.SignUpRequestDto;
import com.ItsTime.ItNovation.domain.user.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor //final 붙은 필드에 대해 생성자 생성
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ResponseEntity<?> join(SignUpRequestDto signUpRequestDto) {
        log.info("회원가입");
        String result = validateDuplicateUser(signUpRequestDto);
        log.info(result);
        if (StringUtils.hasText(result)) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                     //wrapper 타입만 null 이 들어갈 수 있음
        } else {
            User user = User.builder()
                    .email(signUpRequestDto.getEmail())
                    .password(signUpRequestDto.getPassword())
                    .role(Role.GUEST)
                    .build();

            user.passwordEncode(passwordEncoder);

            userRepository.save(user);
            log.info(user.getEmail() + " 회원가입 성공");
            return ResponseEntity.ok(null);
        }

    }


    private String validateDuplicateUser(SignUpRequestDto signUpRequestDto) {
        Optional<User> findEmail = userRepository.findByEmail(signUpRequestDto.getEmail());

        String errorMessage = "";
        if (findEmail.isPresent()) {
            errorMessage += "이미 가입된 이메일입니다.";
        }
        return errorMessage;

    }


}
