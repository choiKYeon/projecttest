package org.example.oauth2.user.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.oauth2.user.OAuth2UserUnlink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//  네이버 연동을 해제하기 위한 구문
@RequiredArgsConstructor
@Component
public class NaverOAuth2UserUnlink implements OAuth2UserUnlink {

    private static final String URL = "https://nid.naver.com/oauth2.0/token";

    private final RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    @Override
    public void unlink(String accessToken) {

        String url = URL +
                "?service_provider=NAVER" +
                "&grant_type=delete" +
                "&client_id=" +
                clientId +
                "&client_secret=" +
                clientSecret +
                "&access_token=" +
                accessToken;

        UnlinkResponse response = restTemplate.getForObject(url, UnlinkResponse.class);

        if (response != null && !"success".equalsIgnoreCase(response.getResult())) {
            throw new RuntimeException("Failed to Naver Unlink");
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class UnlinkResponse {
        @JsonProperty("access_token")
        private final String accessToken;
        private final String result;
    }
}