package com.cetc.security.app.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class OpenIdCodeAuthenticationToken extends AbstractAuthenticationToken {

    public static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;


    private String provideId;

    /*
    openid : cetc_userconnection's provideUserId
     */
    public OpenIdCodeAuthenticationToken(String openId,String provideId) {
        super((Collection)null);
        this.principal = openId;
        this.provideId = provideId;
        this.setAuthenticated(false);
    }

    public OpenIdCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public String getProvideId() {
        return provideId;
    }

}
