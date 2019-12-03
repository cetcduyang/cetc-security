package com.cetc.security.browser;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;

/*
 social ConnectController提供了用户对第三方软件的绑定、解绑等服务
 */

@Controller
public class BrowserConnectController extends ConnectController{

    public BrowserConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

}
