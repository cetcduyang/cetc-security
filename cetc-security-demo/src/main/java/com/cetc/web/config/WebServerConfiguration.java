package com.cetc.web.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class WebServerConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.addConnectorCustomizers((connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setMaxThreads(800);
            protocol.setMinSpareThreads(100);
            protocol.setMaxConnections(1000);
            protocol.setConnectionTimeout(20000);
            protocol.setDisableUploadTimeout(true);
            protocol.setAcceptCount(100);
            protocol.setSSLEnabled(false);

        }));

//        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//            @Override
//            public void customize(Connector connector) {
//                Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//                protocol.setMaxThreads(200);
//                protocol.setMaxConnections(1000);
//            }
//        });
        return factory;
    }
}