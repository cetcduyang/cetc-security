package com.cetc.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/*
    对应第6步api的实现获得QQUserInfo对象（服务提供商用户基本信息）。
    先getOpenId，再getUserInfo
 */

public class QQimpl extends AbstractOAuth2ApiBinding implements QQ {

    private String appId;
    private String openId;
    private static final String URL_GET_OPENID =
            "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String URL_GET_USER_INFO =
            "https://graph.qq.com/user/get_user_info?" +
                    "&oauth_consumer_key=%s" +
                    "&openid=%s";
    public QQimpl(String accessToken,String AppId){
        //restTemplate使用ACCESS_TOKEN_PARAMETER作为查询参数
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID,accessToken);
        String result = getRestTemplate().getForObject(url,String.class);
        System.out.println(" QQimpl result:"+result);
        this.openId = StringUtils.substringBetween(result,"\"openid\"\":","\"}");

    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public QQUserInfo getUserInfo(){
        String url = String.format(URL_GET_USER_INFO,appId,openId);
        String resutl = getRestTemplate().getForObject(url,String.class);
        System.out.println("QQimpl "+resutl);
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(resutl,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
           throw new RuntimeException("获取用户信息失败"+e);
        }
    }
}
