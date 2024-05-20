package org.example.member.service;


import lombok.RequiredArgsConstructor;
import org.example.global.encrypt.EncryptionUtils;
import org.example.global.redis.refreshToken.service.RefreshTokenService;
import org.example.member.entity.Member;
import org.example.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionUtils encryptionUtils;

    //    유저 아이디를 통해 member를 찾는 구문 (Dto값을 반환)
    public Optional<Member> findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        return Optional.ofNullable(member);
//        return memberRepository.findByUsername(username)
//                .map(member -> new MemberDto(member.getName(), member.getUsername(), member.getEmail()));
    }

    //    유저를 확인하는 구문 (entity값을 반환)
    public boolean memberCheck(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member checkMember = member.orElse(null);

        if (checkMember != null) {
            return passwordEncoder.matches(password, checkMember.getPassword());
        } else {
            return false;
        }
    }

//    로그인 시 해당 유저의 refreshToken을 저장하는 구문
    public void saveRefreshToken (String refreshToken, String username){

        refreshTokenService.saveRefreshToken(refreshToken, username);
    }

    //    회원가입 시 유저를 생성하는 구문
    public Member createMember(String name, String username, String password, String email) {
        Member member = Member.builder()
                .name(name)
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        this.memberRepository.save(member);

        return member;
    }
//    Id로 유저 조회
    public Optional<Member> findById(Long id) {
        return this.memberRepository.findById(id);
    }
}
