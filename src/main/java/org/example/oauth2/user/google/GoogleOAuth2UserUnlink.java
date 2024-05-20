package org.example.oauth2.user.google;

import lombok.RequiredArgsConstructor;
import org.example.oauth2.user.OAuth2UserUnlink;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//  구글 연동 해제를 위한 구문
@RequiredArgsConstructor
@Component
public class GoogleOAuth2UserUnlink implements OAuth2UserUnlink {

    private static final String URL = "https://oauth2.googleapis.com/revoke";
    private final RestTemplate restTemplate;

    @Override
    public void unlink(String accessToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", accessToken);
        restTemplate.postForObject(URL, params, String.class);
    }
}
