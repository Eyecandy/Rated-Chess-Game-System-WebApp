package io.muic.designpattern.configurations;

import io.muic.designpattern.components.DisconnectInterceptor;
import io.muic.designpattern.components.SubscribeInterceptor;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.SubscriberService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChessService chessService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/dest");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/web_socket").setAllowedOrigins("*");
        registry.addEndpoint("/web_socket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        DisconnectInterceptor disconnectInterceptor = new DisconnectInterceptor(subscriberService,userService,chessService);
        SubscribeInterceptor subscribeInterceptor = new SubscribeInterceptor(subscriberService);
        registration.setInterceptors(subscribeInterceptor,disconnectInterceptor);
    }
}