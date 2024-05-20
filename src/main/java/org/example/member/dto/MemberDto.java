package org.example.member.dto;

import lombok.*;
import org.example.global.baseentity.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto extends BaseEntity {

    private Long id;

    //    이름
    private String name;

    //    아이디
    private String username;

    //    이메일
    private String email;

    //    권한
    private Collection<? extends GrantedAuthority> authorities;

    //    토큰 생성 정보
    public Map<String, Object> toClaims() {
        return Map.of(
                "id", getId(),
                "name", getName(),
                "username", getUsername(),
                "email", getEmail(),
                "authorities", getAuthorities()
        );
    }

}
