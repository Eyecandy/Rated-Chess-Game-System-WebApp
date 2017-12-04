package io.muic.designpattern.components;

import io.muic.designpattern.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscribeInterceptor implements ChannelInterceptor {
    private final SubscriberService subscriberService;

    @Autowired
    public SubscribeInterceptor(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("preSend Subscribe");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand().getMessageType() == SimpMessageType.CONNECT) {
            System.out.println("SUBSCRIBED");
            accessor.setLeaveMutable(true);
            List<String> tokenList = accessor.getNativeHeader("user");
            System.out.println(tokenList);
            System.out.println(subscriberService);
            subscriberService.register(accessor.getSessionId(),tokenList.get(0));
            return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

    }

    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        return false;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        return null;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

    }

}
