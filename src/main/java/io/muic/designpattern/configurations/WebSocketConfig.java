package io.muic.designpattern.configurations;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.SubscriberService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    UserService userService;

    @Autowired
    ChessService chessService;

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


        registration.setInterceptors(new ChannelInterceptorAdapter() {
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//                System.out.println("Sub ID : " + accessor.getSubscriptionId());
//                System.out.println("Dest " + accessor.getDestination());
                List<String> tokenList = accessor.getNativeHeader("user");
                String token = null;
                System.out.println(accessor.getCommand().getMessageType());
                if (accessor.getCommand().getMessageType() == SimpMessageType.DISCONNECT) {
                    String userString = subscriberService.getSubscribers().get(accessor.getSessionId());
                    User user = userService.findUserByUsername(userString);
                    List<Chess> onePlayerGames = chessService.getOnePlayerGames(user);
                    for (Chess game : onePlayerGames){
                        chessService.delete(game.getId());
                    }
                    subscriberService.removeSession(accessor.getSessionId(),userString);

                }
                if (tokenList == null || tokenList.size() < 1) {
                    return message;
                } else {
                    token = tokenList.get(0);
                    if (token == null) {
                        return message;
                    }
                }
                //Principal principal = new PrincipalImpl(tokenList.get(0));
                accessor.setLeaveMutable(true);
                //subscriberService.getSubscribers().put(accessor.getSessionId(),tokenList.get(0));
                subscriberService.register(accessor.getSessionId(),tokenList.get(0));
                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            }
        });
    }
}