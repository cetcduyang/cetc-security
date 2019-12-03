package com.cetc.security.core.social.qq.connect;

import com.cetc.security.core.social.qq.api.QQ;
import com.cetc.security.core.social.qq.api.QQimpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    //对应第一步（将用户导向认证服务器的地址,获取授权码的地址：Authorization Code）
    private static final String URL_OAUTHOREIZE = "https://graph.qq.com/oauth2.0/authorize";
    //对应第四步（通过第一步获取到授权码Authorization Code去此地址申请令牌Access Token的地址）
    private static final String URL_OAUTHTOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId,appSecret,URL_OAUTHOREIZE,URL_OAUTHTOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQimpl(accessToken,appId);
    }
}
