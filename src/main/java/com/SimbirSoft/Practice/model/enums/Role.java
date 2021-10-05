package com.SimbirSoft.Practice.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
