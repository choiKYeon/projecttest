package org.example.oauth2.user.kakao;

import lombok.RequiredArgsConstructor;
import org.example.oauth2.user.OAuth2UserUnlink;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//  카카오 연동 해제를 위한 구문
@RequiredArgsConstructor
@Component
public class KakaoOAuth2UserUnlink implements OAuth2UserUnlink {

    private static final String URL = "https://kapi.kakao.com/v1/user/unlink";
    private final RestTemplate restTemplate;

    @Override
    public void unlink(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Object> entity = new HttpEntity<>("", headers);
        restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
    }
}
