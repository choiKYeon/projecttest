package org.example.oauth2.user;

import lombok.RequiredArgsConstructor;
import org.example.oauth2.exception.OAuth2AuthenticationProcessingException;
import org.example.oauth2.user.google.GoogleOAuth2UserUnlink;
import org.example.oauth2.user.kakao.KakaoOAuth2UserUnlink;
import org.example.oauth2.user.naver.NaverOAuth2UserUnlink;
import org.springframework.stereotype.Component;

//  OAuth2 연동 해제시 OAuth2 제공자 별로 분기해서 연동을 해제해주는 구문
@RequiredArgsConstructor
@Component
public class OAuth2UserUnlinkManager {

    private final GoogleOAuth2UserUnlink googleOAuth2UserUnlink;
    private final KakaoOAuth2UserUnlink kakaoOAuth2UserUnlink;
    private final NaverOAuth2UserUnlink naverOAuth2UserUnlink;

    public void unlink(OAuth2Provider provider, String accessToken) {
        if (OAuth2Provider.GOOGLE.equals(provider)) {
            googleOAuth2UserUnlink.unlink(accessToken);
        } else if (OAuth2Provider.NAVER.equals(provider)) {
            naverOAuth2UserUnlink.unlink(accessToken);
        } else if (OAuth2Provider.KAKAO.equals(provider)) {
            kakaoOAuth2UserUnlink.unlink(accessToken);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Unlink with " + provider.getRegistrationId() + " is not supported");
        }
    }
}
