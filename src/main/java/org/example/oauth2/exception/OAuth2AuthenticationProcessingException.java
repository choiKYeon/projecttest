package org.example.oauth2.exception;

import org.springframework.security.core.AuthenticationException;

//  인증 관련 실패를 위한 예외 클래스
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
