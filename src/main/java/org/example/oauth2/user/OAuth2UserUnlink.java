package org.example.oauth2.user;

//  OAuth2 서비스 별로 연동 해제방법을 통합하기 위한 인터페이스
public interface OAuth2UserUnlink {
    void unlink(String accessToken);
}
