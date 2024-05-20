package org.example.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.*;
import org.example.global.jwt.JwtProvider;
import org.example.global.jwt.JwtUtil;
import org.example.global.redis.refreshToken.service.RefreshTokenService;
import org.example.global.rq.Rq;
import org.example.global.rs.RsData;
import org.example.member.entity.Member;
import org.example.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import static org.example.global.filter.JwtAuthenticationFilter.extractAccessToken;
import static org.example.global.filter.JwtAuthenticationFilter.extractRefreshToken;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class MemberController {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final Rq rq;

    @Data
    public static class MemberJoinData {
        //        이름
        private String name;
        //        아이디
        private String username;
        //        비밀번호
        private String password;
        //        이메일
        private String email;
    }

    @AllArgsConstructor
    @Getter
    public static class JoinResponse {
        private final Member member;
    }

    //    회원가입
    @PostMapping(value = "/join", consumes = APPLICATION_JSON_VALUE)
    public RsData<JoinResponse> joinMember(@Valid @RequestBody MemberJoinData memberJoinData, HttpServletRequest request) {
        Member saveMember = memberService.createMember(memberJoinData.getName(), memberJoinData.getUsername(), memberJoinData.getPassword(), memberJoinData.getEmail());

        return RsData.of("S-1", "가입 성공", new JoinResponse(saveMember));
    }

    @Getter
    public static class loginResponse {

        private String accessToken;
        private String refreshToken;

        public loginResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Data
    public static class loginRequest {
        //        아이디
        private String username;
        //        비밀번호
        private String password;
    }

    //    로그인 시 토큰 발급하는 구문
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public RsData<loginResponse> login(@Valid @RequestBody loginRequest loginRequest, HttpServletResponse resp) throws Exception {
        boolean loginMember = memberService.memberCheck(loginRequest.getUsername(), loginRequest.getPassword());

        if (loginMember) {
            String accessToken = jwtUtil.genAccessToken(loginRequest.getUsername());
            String refreshToken = jwtUtil.genRefreshToken(loginRequest.getUsername());

            memberService.saveRefreshToken(refreshToken, loginRequest.getUsername());

            // 토큰 저장
            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("refreshToken", refreshToken);

            return RsData.of("S-2", "토큰이 생성되었습니다.", null);
        } else {
            return RsData.of("일치하지 않음", null);
        }
    }

    @PostMapping(value = "/logout", consumes = APPLICATION_JSON_VALUE)
    public RsData<loginResponse> logout(HttpServletRequest req) {
        String token = extractRefreshToken(req);
        String username = jwtProvider.getUsername(token);
        refreshTokenService.deleteRefreshToken(token);

        rq.removeCookie("accessToken");
        rq.removeCookie("refreshToken");

        return RsData.of("S-3", "토큰이 삭제되었습니다.", null);
    }

    //    현재 로그인한 유저
    @AllArgsConstructor
    @Getter
    public static class loginUser {
        private final Member member;
    }

    @GetMapping(value = "/loginUser", consumes = APPLICATION_JSON_VALUE)
    public RsData<?> loginUser(HttpServletRequest request) {
        String token = extractAccessToken(request); //헤더에 담긴 쿠키에서 토큰 요청

        Long userId = ((Integer) jwtProvider.getClaims(token).get("id")).longValue(); //유저의 아이디 값

        Member loginUser = this.memberService.findById(userId).orElse(null);
        return RsData.of("S-99", "현재 로그인 유저", new loginUser(loginUser));
    }

    //    refreshToken 검증 구문
    @GetMapping(value = "/refresh", consumes = APPLICATION_JSON_VALUE)
    public RsData<?> refresh(HttpServletRequest request) {
        String token = extractRefreshToken(request);

        Long userId = ((Integer) jwtProvider.getClaims(token).get("id")).longValue();

        Member loginUser = this.memberService.findById(userId).orElse(null);

        return RsData.of("S-98", "refresh토큰 만료", new loginUser(loginUser));
    }
}