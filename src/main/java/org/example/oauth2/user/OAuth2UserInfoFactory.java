package org.example.oauth2.user;

import org.example.oauth2.exception.OAuth2AuthenticationProcessingException;
import org.example.oauth2.user.google.GoogleOAuth2UserInfo;
import org.example.oauth2.user.kakao.KakaoOAuth2UserInfo;
import org.example.oauth2.user.naver.NaverOAuth2UserInfo;

import java.util.Map;

//  OAuth2 인증시 액세스 토큰으로 사용자 정보를 가져왔을 때, OAuth2 제공자 별로 분기하여 Info 인터페이스 구현체를 호출하여, 객체를 생성해주는 구문
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   String accessToken,
                                                   Map<String, Object> attributes) {
        if (OAuth2Provider.GOOGLE.getRegistrationId().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(accessToken, attributes);
        } else if (OAuth2Provider.NAVER.getRegistrationId().equals(registrationId)) {
            return new NaverOAuth2UserInfo(accessToken, attributes);
        } else if (OAuth2Provider.KAKAO.getRegistrationId().equals(registrationId)) {
            return new KakaoOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported");
        }
    }
}
