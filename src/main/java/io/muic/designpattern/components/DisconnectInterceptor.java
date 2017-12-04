package io.muic.designpattern.components;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.SubscriberService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisconnectInterceptor implements ChannelInterceptor {

    private final SubscriberService subscriberService;

    private final UserService userService;

    private final ChessService chessService;

    @Autowired
    public DisconnectInterceptor(SubscriberService subscriberService, UserService userService, ChessService chessService) {
        this.subscriberService = subscriberService;
        this.userService = userService;
        this.chessService = chessService;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("Presend DISCONNECT");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand().getMessageType() == SimpMessageType.DISCONNECT) {
            System.out.println("------Disconnected-------");
            String userString = subscriberService.getSubscribers().get(accessor.getSessionId());
            User user = userService.findUserByUsername(userString);
            List<Chess> onePlayerGames = chessService.getOnePlayerGames(user);
            for (Chess game : onePlayerGames){
                chessService.delete(game.getId());
            }
            subscriberService.removeSession(accessor.getSessionId(),userString);
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
