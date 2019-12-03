package com.cetc.security.app.openid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

public class OpenIdCodeAuthenticationProvider implements AuthenticationProvider {


    private SocialUserDetailsService userDetailsService;
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdCodeAuthenticationToken authenticationToken = (OpenIdCodeAuthenticationToken) authentication;
        Set<String> providerUserIds = new HashSet<>();
        providerUserIds.add((String) authenticationToken.getPrincipal());
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProvideId(),providerUserIds);
        if (CollectionUtils.isEmpty(userIds)){
            throw new InternalAuthenticationServiceException("usersConnectionRepository无法获取用户信息！");
        }
        String userId = userIds.iterator().next();
        UserDetails user = userDetailsService.loadUserByUserId(userId);
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取应用的用户信息");
        }
        OpenIdCodeAuthenticationToken authenticationResult = new OpenIdCodeAuthenticationToken(user,user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}
