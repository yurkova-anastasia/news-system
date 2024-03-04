package ru.clevertec.user_service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Role {

    JOURNALIST(List.of(
            Authority.WRITE_NEWS.getGrantedAuthority(),
            Authority.UPDATE_NEWS.getGrantedAuthority(),
            Authority.DELETE_NEWS.getGrantedAuthority())
    ),
    SUBSCRIBER(List.of(
            Authority.WRITE_COMMENTS.getGrantedAuthority(),
            Authority.UPDATE_COMMENTS.getGrantedAuthority(),
            Authority.DELETE_COMMENTS.getGrantedAuthority())
    ),
    ADMIN(List.of(
            Authority.WRITE_NEWS.getGrantedAuthority(),
            Authority.UPDATE_NEWS.getGrantedAuthority(),
            Authority.DELETE_NEWS.getGrantedAuthority(),
            Authority.WRITE_COMMENTS.getGrantedAuthority(),
            Authority.UPDATE_COMMENTS.getGrantedAuthority(),
            Authority.DELETE_COMMENTS.getGrantedAuthority())
    );

    private final List<GrantedAuthority> authorities;
}
