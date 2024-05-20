package org.example.oauth2.user;

import java.util.Map;

//  OAuth2 제공자 별로 다른 구조를 통합하기 위한 인터페이스
public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();
}
