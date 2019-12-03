package com.cetc.security.core.support;

public class SocialUserInfo {
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    private String providerId;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getProviderUserId() {
        return ProviderUserId;
    }

    public void setProviderUserId(String providerUserId) {
        ProviderUserId = providerUserId;
    }

    private String ProviderUserId;
    private String nickname;
    private String headimg;
}
