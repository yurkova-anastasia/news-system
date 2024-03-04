package ru.clevertec.user_service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Authority {

    WRITE_COMMENTS(new SimpleGrantedAuthority("WRITE_COMMENTS")),
    UPDATE_COMMENTS(new SimpleGrantedAuthority("UPDATE_COMMENTS")),
    DELETE_COMMENTS(new SimpleGrantedAuthority("DELETE_COMMENTS")),
    WRITE_NEWS(new SimpleGrantedAuthority("WRITE_NEWS")),
    UPDATE_NEWS(new SimpleGrantedAuthority("UPDATE_NEWS")),
    DELETE_NEWS(new SimpleGrantedAuthority("DELETE_NEWS"));

    private final GrantedAuthority grantedAuthority;
}